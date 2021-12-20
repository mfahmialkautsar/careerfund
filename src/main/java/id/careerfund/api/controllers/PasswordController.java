package id.careerfund.api.controllers;

import id.careerfund.api.domains.models.requests.PasswordChangeRequest;
import id.careerfund.api.domains.models.requests.ResetPassword;
import id.careerfund.api.domains.models.requests.EmailRequest;
import id.careerfund.api.domains.models.responses.ApiResponse;
import id.careerfund.api.services.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class PasswordController extends HandlerController {
    private final UserService userService;

    @PutMapping("")
    public ResponseEntity<ApiResponse> changePassword(Principal principal, @Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
        try {
            userService.changePassword(principal, passwordChangeRequest);
            return ResponseEntity.ok(ApiResponse.builder().message("Password changed").build());
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your old password is wrong",
                    e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
                    "Failed to change password. Please try again later.");
        }
    }

    @PostMapping("/forgot") // send link reset
    public ResponseEntity<ApiResponse> sendEmailForgot(@Valid @RequestBody EmailRequest emailRequest) {
        try {
            userService.sendResetPasswordEmail(emailRequest);
            return ResponseEntity
                    .ok(ApiResponse.builder().message("Please check your email for OTP verification").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User not found");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
                    "Failed to reset password. Please try again later.");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody ResetPassword resetPassword) {
        try {
            userService.resetPassword(resetPassword);
            return ResponseEntity.ok(ApiResponse.builder().message("Password has been updated").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Token not found");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
                    "Failed to update password. Please try again later.");
        }
    }
}
