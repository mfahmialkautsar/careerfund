package id.careerfund.api.controllers;

import id.careerfund.api.services.ForgotPasswordService;
import id.careerfund.api.domains.models.requests.EmailRequest;
import id.careerfund.api.domains.models.requests.UpdatePassword;
import id.careerfund.api.domains.models.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class ForgetPasswordController extends HandlerController {
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/forgot") // send link reset
    public ResponseEntity<ApiResponse> sendEmailForgot(@RequestBody EmailRequest model) {
        try {
            forgotPasswordService.sendEmailForgot(model);
            return ResponseEntity
                    .ok(ApiResponse.builder().message("Please check your email for OTP verification").build());
        } catch (Exception e) {
            if (e.getMessage().equals("User Not Found")) {
                return ResponseEntity.notFound().build();
            } else
                return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String token,
            @RequestBody UpdatePassword model) {
        try {
            forgotPasswordService.resetPassword(token, model);
            return ResponseEntity.ok(ApiResponse.builder().message("Password has been updated").build());
        } catch (Exception e) {
            if (e.getMessage().equals("Token Not Found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }
}
