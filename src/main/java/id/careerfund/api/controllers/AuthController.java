package id.careerfund.api.controllers;

import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.*;
import id.careerfund.api.domains.models.ResponseTemplate;
import id.careerfund.api.services.RefreshTokenService;
import id.careerfund.api.services.TokenService;
import id.careerfund.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController extends HandlerController {
    private final UserService userService;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @GetMapping("/user")
    public ResponseEntity<Principal> getUser(Principal principal) {
        return ResponseEntity.ok(principal);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> registerUser(@Valid @RequestBody UserRegister user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/register").toUriString());
        try {
            TokenResponse tokenResponse = userService.registerUser(user);
            return ResponseEntity.created(uri).body(tokenResponse);
        } catch (Exception e) {
            if (e.getMessage().equals("EMAIL_UNAVAILABLE")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is taken", e.getCause());
            }
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @PostMapping("/email-availability")
    public ResponseEntity<Boolean> checkEmailAvailability(@Valid @RequestBody EmailAvailability availability) {
        boolean getIsAvailable = userService.getIsEmailAvailable(availability.getEmail());
        return ResponseEntity.ok(getIsAvailable);
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> signIn(@RequestBody SignInRequest signInRequest) {
        try {
            TokenResponse result = tokenService.signIn(signInRequest);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            if (e.getMessage().equals("User Not Found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenResponse> getNewToken(@Valid @RequestBody NewTokenRequest request) {
        try {
            TokenResponse tokenResponse = tokenService.getNewToken(request);
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            if (e.getMessage().equals("Token Not Found")) {
                return ResponseEntity.notFound().build();
            } else return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/signout")
    public ResponseEntity<ResponseTemplate> signOut(@Valid @RequestBody SignOutRequest signOutRequest) {
        try {
            ResponseTemplate result = refreshTokenService.signOut(signOutRequest);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            if (e.getMessage().equals("Token Not Found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }
}
