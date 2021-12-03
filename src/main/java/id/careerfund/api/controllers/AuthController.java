package id.careerfund.api.controllers;

import com.auth0.jwt.exceptions.TokenExpiredException;
import id.careerfund.api.domains.models.*;
import id.careerfund.api.domains.models.ResponseTemplate;
import id.careerfund.api.services.RefreshTokenService;
import id.careerfund.api.services.TokenService;
import id.careerfund.api.services.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthController extends HandlerController {
    private final UserService userService;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody UserRegister user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/register").toUriString());
        try {
            userService.registerUser(user);
            return ResponseEntity.created(uri).body(ApiResponse.success("Please check your email for OTP verification"));
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().equals("EMAIL_UNAVAILABLE")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is taken", e.getCause());
            }
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @PostMapping("/email-availability")
    public ResponseEntity<EmailAvailability> checkEmailAvailability(@Valid @RequestBody EmailRequest availability) {
        boolean getIsAvailable = userService.getIsEmailAvailable(availability.getEmail());
        return ResponseEntity.ok(new EmailAvailability(getIsAvailable));
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        try {
            TokenResponse result = userService.signIn(signInRequest);
            if (result == null) throw new DisabledException(null);
            return ResponseEntity.ok(result);
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.TEMPORARY_REDIRECT, "Please input OTP sent to your email", e.getCause());
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password is incorrect", e.getCause());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found", e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Failed to login", e.getCause());
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenResponse> getNewToken(@Valid @RequestBody NewTokenRequest request) {
        try {
            TokenResponse tokenResponse = tokenService.getNewToken(request);
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().equals("Token Not Found")) {
                return ResponseEntity.notFound().build();
            }
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Failed", e.getCause());
        }
    }

    @PutMapping("/signout")
    public ResponseEntity<ResponseTemplate> signOut(@Valid @RequestBody SignOutRequest signOutRequest) {
        try {
            ResponseTemplate result = refreshTokenService.signOut(signOutRequest);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().equals("Token Not Found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }

//    @PostMapping("/signup/otp/request-email")
//    public ResponseEntity<ApiResponse> requestSignUpOTP(@Valid @RequestBody EmailRequest emailRequest) {
//        try {
//            this.userService.sendVerificationEmail(emailRequest);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Failed sending email, try again", e.getCause());
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credential not found", e.getCause());
//        }
//        return ResponseEntity.ok(ApiResponse.success("Please check your email for OTP verification"));
//    }

    @PostMapping("/signup/otp/verify")
    public ResponseEntity<TokenResponse> verifySignUpOTP(@Valid @RequestBody OtpRequest otpRequest) {
        try {
            TokenResponse tokenResponse = this.userService.verifyUser(otpRequest);
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            if (e instanceof NotFoundException || e instanceof TokenExpiredException)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Either OTP not found or expired", e.getCause());
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Failed to verify OTP. Please try again later.");
        }
    }
}
