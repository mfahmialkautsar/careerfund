package id.careerfund.api.controllers.forgetpassword;

import id.careerfund.api.domains.entities.User;
import id.careerfund.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService{
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

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
}
