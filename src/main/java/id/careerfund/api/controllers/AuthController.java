package id.careerfund.api.controllers;

import id.careerfund.api.configurations.jwt.JwtServiceImpl;
import id.careerfund.api.domains.entities.RefreshToken;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.*;
import id.careerfund.api.domains.models.ResponseTemplate;
import id.careerfund.api.services.RefreshTokenService;
import id.careerfund.api.services.TokenService;
import id.careerfund.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;
    private final JwtServiceImpl jwtServiceImpl;
    private final RefreshTokenService refreshTokenService;
    ResponseTemplate response = new ResponseTemplate();


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
    public ResponseEntity<TokenResponse> signIn(@RequestBody SignInRequest signInRequest) {
        try {
            String email = signInRequest.getEmail();
            String password = signInRequest.getPassword();

            if (ObjectUtils.isEmpty(email) || ObjectUtils.isEmpty(password)) {
                return ResponseEntity.badRequest().build();
            }

            if (!tokenService.authUser(email, password) ) {
                return ResponseEntity.notFound().build();
            }

            List<String> roles = tokenService.roles(email);

            String jwtToken = jwtServiceImpl.generateToken(email);
            String refreshToken = tokenService.refreshToken(email);

            return ResponseEntity.ok(new TokenResponse(jwtToken, roles, refreshToken));

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenResponse> getNewToken(@Valid @RequestBody NewTokenRequest request) throws Exception {
        String refreshToken = request.getRefreshtoken();

        RefreshToken token = refreshTokenService.findByToken(refreshToken);
        if (!ObjectUtils.isEmpty(token)){
            if (refreshTokenService.verifyExpiration(token)) {
                String newToken = jwtServiceImpl.generateToken(token.getUser().getUsername());
                List<String> roles = tokenService.roles(token.getUser().getEmail());
                return ResponseEntity.ok(new TokenResponse(newToken,  roles, refreshToken));
            } else return ResponseEntity.notFound().build();

        } else return ResponseEntity.notFound().build();
    }

    @PutMapping("/signout")
    public ResponseEntity<ResponseTemplate> signOut(@Valid @RequestBody SignOutRequest signOutRequest) {
        String token = signOutRequest.getRefreshtoken();

        try {
            if (ObjectUtils.isEmpty(refreshTokenService.findByToken(token)) ) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.responseNotFound());
            }
            refreshTokenService.deleteByToken(token);
            return ResponseEntity.ok(response.responseSuccess());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(response.responseBadRequest(e.getMessage()));
        }
    }
}
