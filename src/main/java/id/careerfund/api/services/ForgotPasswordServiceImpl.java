package id.careerfund.api.services;

import id.careerfund.api.configurations.EmailSenderConfig;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.RequestOtpPassword;
import id.careerfund.api.domains.models.ResponseTemplate;
import id.careerfund.api.domains.models.UpdatePassword;
import id.careerfund.api.repositories.UserRepository;
import id.careerfund.api.utils.templates.EmailTemplate;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailTemplate emailTemplate;
    private final EmailSenderConfig emailSenderConfig;

    @Value("${token.password.reset_minutes:}")
    public int expiredToken;

    @Value("${myapp.url:}")
    public String url;

    @Override
    public Boolean setUserPassword(String token, String password) {
        User user = userRepo.findOneByOTP(token);
        if (!ObjectUtils.isEmpty(user)) {
            if (checkTokenExpire(user)) {
                user.setPassword(passwordEncoder.encode(password.replaceAll("\\s+", "")));
                user.setOtpExpiredDate(null);
                user.setOtp(null);
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean checkTokenExpire(User user) {
        return user.getOtpExpiredDate().after(new Date());
    }

    @Override
    public Boolean checkUser(String email) {
        User user = userRepo.findByEmail(email);
        return user != null;
    }

    @Override
    public Boolean checkOtp(String email) {
        String otp = Objects.requireNonNull(userRepo.findByEmail(email)).getOtp();
        return otp != null;
    }

    @Override
    public String saveOtp(String email, int expiredToken) {
        User user = userRepo.findByEmail(email);

        User search;
        String otp;
        do {
            otp = randomString(6, true);
            search = userRepo.findOneByOTP(otp);
        } while (search != null);

        Date dateNow = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(dateNow);
        calendar.add(Calendar.MINUTE, expiredToken);
        Date expirationDate = calendar.getTime();

        assert user != null;
        user.setOtp(otp);
        user.setOtpExpiredDate(expirationDate);

        userRepo.save(user);
        return otp;
    }

    @Override
    @SuppressWarnings("SpellCheckingInspection")
    public String randomString(int size, boolean numberOnly) {
        String saltChars = "1234567890";
        if (!numberOnly) {
            saltChars += "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        }
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < size) {
            int index = (int) (rnd.nextFloat() * saltChars.length());
            salt.append(saltChars.charAt(index));
        }
        return salt.toString();
    }

    @Override
    public void sendEmail(String email, User user) {
        String template = emailTemplate.getResetPassword();

        if (!checkOtp(email)) {
            String otp = saveOtp(email, expiredToken);
            template = template.replaceAll("\\{\\{RESET_LINK}}", url + "/v1/password/update?token=" + otp);
        } else {
            template = template.replaceAll("\\{\\{RESET_LINK}}", user.getOtp());
        }

        emailSenderConfig.sendAsync(user.getUsername(), "Forget Password", template);
    }

    @Override
    public ResponseTemplate resetPassword(String token, UpdatePassword model) throws Exception {
        String newPassword = model.getNewPassword();

        if (ObjectUtils.isEmpty(newPassword)) {
            throw new Exception("Bad Request");
        }

        Boolean result = setUserPassword(token, newPassword);

        if (!result) {
            throw new NotFoundException("Token Not Found");
        }

        return new ResponseTemplate().responseSuccess();
    }

    @Override
    public ResponseTemplate sendEmailForgot(RequestOtpPassword model) throws Exception {

        String email = model.getEmail();
        User user = userRepo.findByEmail(email);

        if (ObjectUtils.isEmpty(email)) {
            throw new Exception("Bad Request");
        }

        if (!checkUser(email)) {
            throw new NotFoundException("User Not Found");
        }

        try {
            sendEmail(email, user);
            return new ResponseTemplate().responseSuccess();
        } catch (Exception e) {
            throw new Exception("Bad Request");
        }
    }
}
