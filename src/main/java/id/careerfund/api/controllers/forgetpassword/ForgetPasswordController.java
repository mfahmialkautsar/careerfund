package id.careerfund.api.controllers.forgetpassword;

import id.careerfund.api.configurations.EmailSenderConfig;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.RequestOtpPassword;
import id.careerfund.api.domains.models.UpdatePassword;
import id.careerfund.api.configurations.ResponseDetailConfig;
import id.careerfund.api.domains.models.ResponseTemplate;
import id.careerfund.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/password")
public class ForgetPasswordController {
    ResponseDetailConfig responseDetailConfig = new ResponseDetailConfig();

    private final EmailSenderConfig emailSenderConfig;

    private final EmailTemplate emailTemplate;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${security.token.expired.minute:}")
    public int expiredToken;

    @Value("${url:}")
    public String url;

    @Autowired
    public ForgetPasswordController(EmailSenderConfig emailSenderConfig,
                                    EmailTemplate emailTemplate,
                                    UserRepository userRepository,
                                    PasswordEncoder passwordEncoder) {
        this.emailSenderConfig = emailSenderConfig;
        this.emailTemplate = emailTemplate;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/forgot")//send OTP
    public ResponseEntity<Object> sendEmailPassword(@RequestBody RequestOtpPassword user) {

        if (ObjectUtils.isEmpty(user.getEmail()))
            return ResponseEntity.badRequest().body(new ResponseTemplate("Email required",  responseDetailConfig.getCodeRequired()));
        User found = userRepository.findByEmail(user.getEmail());

        if (found == null) return ResponseEntity.status(Integer.parseInt(responseDetailConfig.getCodeNotFound())).body("email " + responseDetailConfig.getMessageNotFound());

        String template = emailTemplate.getResetPassword();
        if (ObjectUtils.isEmpty(found.getOtp())) {
            User search;
            String otp;
            do {
                otp = StringGeneratorOtp.randomString(6, true);
                search = userRepository.findOneByOTP(otp);
            } while (search != null);
            Date dateNow = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateNow);
            calendar.add(Calendar.MINUTE, expiredToken);
            Date expirationDate = calendar.getTime();

            found.setOtp(otp);
            found.setOtpExpiredDate(expirationDate);
            template = template.replaceAll("\\{\\{RESET_LINK}}", url + "/v1/password/update?token=" + otp);
            userRepository.save(found);
        } else {
            template = template.replaceAll("\\{\\{RESET_LINK}}", found.getOtp());
        }
        emailSenderConfig.sendAsync(found.getUsername(), "Forget Password", template);

        return ResponseEntity.ok(new ResponseTemplate(responseDetailConfig.getMessageSuccess(), responseDetailConfig.getCodeSuccess()));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> resetPassword(@RequestParam String token, @RequestBody UpdatePassword model) {
        if (model.getNewpassword() == null) return ResponseEntity.badRequest().body(new ResponseTemplate("New password is required",  responseDetailConfig.getCodeRequired()));

        User user = userRepository.findOneByOTP(token);
        Date dateNow = new Date();

        if (user == null) return ResponseEntity.status(Integer.parseInt(responseDetailConfig.getCodeNotFound())).body(new ResponseTemplate("Token not valid", responseDetailConfig.getCodeNotFound()));

        if (!user.getOtpExpiredDate().after(dateNow)) {
            return ResponseEntity.status(Integer.parseInt(responseDetailConfig.getCodeNotFound())).body(new ResponseTemplate("Token not valid", responseDetailConfig.getCodeNotFound()));
        }

        user.setPassword(passwordEncoder.encode(model.getNewpassword().replaceAll("\\s+", "")));
        user.setOtpExpiredDate(null);
        user.setOtp(null);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseTemplate(e.getMessage(), responseDetailConfig.getCodeRequired()));
        }
        return ResponseEntity.ok(new ResponseTemplate(responseDetailConfig.getMessageSuccess(), responseDetailConfig.getCodeSuccess()));
    }
}
