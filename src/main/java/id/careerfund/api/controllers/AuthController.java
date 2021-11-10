package id.careerfund.api.controllers;

import id.careerfund.api.configurations.jwt.JwtService;
import id.careerfund.api.domains.entities.RefreshToken;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.*;
import id.careerfund.api.configurations.ResponseDetailConfig;
import id.careerfund.api.domains.models.ResponseTemplate;
import id.careerfund.api.services.RefreshTokenService;
import id.careerfund.api.utils.converters.ModelConverter;
import id.careerfund.api.repositories.UserRepository;
import id.careerfund.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class AuthController {

    ResponseDetailConfig responseDetailConfig = new ResponseDetailConfig();
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepo;

    @Autowired
    RefreshTokenService refreshTokenService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegister user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/register").toUriString());
        try {
            userService.registerUser(user);
        } catch (Exception e) {
            if (e.getMessage().equals("EMAIL_UNAVAILABLE")) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Email is taken"));
            }
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/email-availability")
    public ResponseEntity<Boolean> checkEmailAvailability(@Valid @RequestBody EmailAvailability availability) {
        boolean getIsAvailable = userService.getIsEmailAvailable(availability.getEmail());
        return ResponseEntity.ok(getIsAvailable);
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> generateToken (@RequestBody AuthRequest authRequest) {
        try {
            User user = userRepo.findByEmail(authRequest.getEmail());

            if (ObjectUtils.isEmpty(user)) {
                return ResponseEntity.badRequest().body(new ResponseTemplate("Email not found / required",  responseDetailConfig.getCodeRequired()));
            }
            if (ObjectUtils.isEmpty(authRequest.getPassword())) {
                return ResponseEntity.badRequest().body(new ResponseTemplate("Password required",  responseDetailConfig.getCodeRequired()));
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

            Collection<String> roles = ModelConverter.toStringRoleList(Objects.requireNonNull(userRepo.findByEmail(authRequest.getEmail())).getRoles());

            return ResponseEntity.ok(new TokenResponse(
                    responseDetailConfig.getCodeSuccess(),
                    responseDetailConfig.getMessageSuccess(),
                    jwtService.generateToken(authRequest.getEmail()),
                    responseDetailConfig.getTokenPrefix(),
                    roles,
                    refreshToken.getToken()));

        } catch (Exception e) {
            return ResponseEntity.status(Integer.parseInt(responseDetailConfig.getCodeNotFound())).body(new ResponseTemplate("Email or Password " + responseDetailConfig.getMessageNotFound() ,  responseDetailConfig.getCodeNotFound()));
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {

        String requestRefreshToken = request.getRefreshtoken();
            return refreshTokenService.findByToken(requestRefreshToken)
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUser)
                    .map(user -> {
                        String token = jwtService.generateToken(user.getUsername());
                        return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                    })
                    .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/signout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
        if (!refreshTokenService.findByToken(logOutRequest.getRefreshtoken()).isPresent()) {
            return ResponseEntity.status(404).body(new ResponseTemplate(responseDetailConfig.getMessageNotFound(), responseDetailConfig.getCodeNotFound()));
        }
        try {
            refreshTokenService.deleteByToken(logOutRequest.getRefreshtoken());
            return ResponseEntity.ok(new ResponseTemplate(responseDetailConfig.getMessageSuccess(), responseDetailConfig.getCodeSuccess()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad Request");
        }
    }
}
