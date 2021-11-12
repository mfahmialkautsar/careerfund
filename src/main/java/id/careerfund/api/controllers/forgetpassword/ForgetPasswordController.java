package id.careerfund.api.controllers.forgetpassword;

import id.careerfund.api.configurations.EmailSenderConfig;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.RequestOtpPassword;
import id.careerfund.api.domains.models.UpdatePassword;
import id.careerfund.api.domains.models.ResponseTemplate;
import id.careerfund.api.repositories.UserRepository;
import id.careerfund.api.utils.templates.EmailTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class ForgetPasswordController {
    private final EmailSenderConfig emailSenderConfig;
    private final EmailTemplate emailTemplate;
    private final UserRepository userRepo;
    private final ForgotPasswordService forgotPasswordService;

    ResponseTemplate responseTemplate = new ResponseTemplate();

    @Value("${security.token.expired.minute:}")
    public int expiredToken;

    @Value("${url:}")
    public String url;

    @PostMapping("/forgot")//send link reset
    public ResponseEntity<ResponseTemplate>  sendEmailForgot(@RequestBody RequestOtpPassword model) {
        String template = emailTemplate.getResetPassword();

        String email = model.getEmail();
        User user = userRepo.findByEmail(email);
        assert user != null;

        if (ObjectUtils.isEmpty(email)) return ResponseEntity.badRequest().build();

        if (!forgotPasswordService.checkUser(email)) return ResponseEntity.notFound().build();

        if (!forgotPasswordService.checkOtp(email)) {
            String otp = forgotPasswordService.saveOtp(email, expiredToken);
            template = template.replaceAll("\\{\\{RESET_LINK}}", url + "/v1/password/update?token=" + otp);
        } else {

            template = template.replaceAll("\\{\\{RESET_LINK}}", user.getOtp());
        }
        emailSenderConfig.sendAsync(user.getUsername(), "Forget Password", template);

        return ResponseEntity.ok(responseTemplate.responseSuccess());
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseTemplate> resetPassword(@RequestParam String token, @RequestBody UpdatePassword model) {
        String newPassword = model.getNewpassword();

        if (ObjectUtils.isEmpty(newPassword)) return ResponseEntity.badRequest().build();

        Boolean result = forgotPasswordService.setUserPassword(token, newPassword);

        if (result) {
            try {
                return ResponseEntity.ok(responseTemplate.responseSuccess());
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        } else return ResponseEntity.notFound().build();

    }
}
