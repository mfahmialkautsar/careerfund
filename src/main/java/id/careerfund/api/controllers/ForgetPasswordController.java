package id.careerfund.api.controllers;

import id.careerfund.api.services.ForgotPasswordService;
import id.careerfund.api.domains.models.RequestOtpPassword;
import id.careerfund.api.domains.models.UpdatePassword;
import id.careerfund.api.domains.models.ResponseTemplate;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class ForgetPasswordController {
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/forgot")//send link reset
    public ResponseEntity<ResponseTemplate> sendEmailForgot(@RequestBody RequestOtpPassword model) {
        try {
            ResponseTemplate result = forgotPasswordService.sendEmailForgot(model);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            if (e.getMessage().equals("User Not Found")) {
                return ResponseEntity.notFound().build();
            } else return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseTemplate> resetPassword(@RequestParam String token, @RequestBody UpdatePassword model) {
        try {
            ResponseTemplate result = forgotPasswordService.resetPassword(token, model);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            if (e.getMessage().equals("Token Not Found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }
}
