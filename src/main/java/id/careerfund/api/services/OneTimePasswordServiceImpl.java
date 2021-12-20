package id.careerfund.api.services;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.bastiaanjansen.otp.HMACAlgorithm;
import com.bastiaanjansen.otp.TOTPGenerator;
import id.careerfund.api.domains.entities.OneTimePassword;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.repositories.OneTimePasswordRepository;
import id.careerfund.api.repositories.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OneTimePasswordServiceImpl implements OneTimePasswordService {
    private final OneTimePasswordRepository oneTimePasswordRepo;
    private final UserRepository userRepo;
    private final Environment environment;

    private TOTPGenerator getGenerator() {
        TOTPGenerator.Builder builder = new TOTPGenerator.Builder(
                Objects.requireNonNull(this.environment.getProperty("totp.secret")).getBytes());

        builder.withPasswordLength(6)
                .withAlgorithm(HMACAlgorithm.SHA256)
                .withPeriod(Duration.ofMinutes(5));
        return builder.build();
    }

    private String getGeneratedStringToken(int length) {
        CharacterRule digits = new CharacterRule(EnglishCharacterData.Alphabetical);

        PasswordGenerator passwordGenerator = new PasswordGenerator();
        return passwordGenerator.generatePassword(length, digits);
    }

    private String getGeneratedStringToken() {
        return getGeneratedStringToken(99);
    }

    @Override
    public String generateOtp(User user) {
        TOTPGenerator totp = getGenerator();
        String generatedPassword = totp.generate();
        if (oneTimePasswordRepo.findByPassword(generatedPassword) != null)
            return generateOtp(user);

        oneTimePasswordRepo.deleteByUser_Email(user.getEmail());

        OneTimePassword oneTimePassword = new OneTimePassword();
        oneTimePassword.setPassword(generatedPassword);
        oneTimePassword.setExpiredAt(LocalDateTime.now().plusMinutes(5));
        oneTimePassword.setUser(user);
        oneTimePasswordRepo.save(oneTimePassword);
        log.info("Generating OTP: {} for {}", oneTimePassword.getPassword(), user.getEmail());
        return oneTimePassword.getPassword();
    }

    @Override
    public OneTimePassword verifyOtp(String otp) throws NotFoundException, TokenExpiredException {
        log.info("Verifying OTP {}", otp);
        OneTimePassword oneTimePassword = oneTimePasswordRepo.findByPassword(otp);
        if (oneTimePassword == null)
            throw new NotFoundException("OTP_NOT_FOUND");
        if (oneTimePassword.getExpiredAt().isBefore(LocalDateTime.now()))
            throw new TokenExpiredException("OTP_EXPIRED");

        oneTimePasswordRepo.delete(oneTimePassword);
        return oneTimePassword;
    }

    @Override
    public String getResetPasswordToken(User user) {
        String password = getGeneratedStringToken();
        user.setOtp(password);
        user.setOtpExpiredDate(LocalDateTime.now().plusDays(1));
        log.info("Generating Reset Password Token: {} for {}", password, user.getEmail());
        return password;
    }

    @Override
    public User verifyResetPasswordToken(String password) throws NotFoundException, TokenExpiredException {
        User user = userRepo.findByOtp(password);
        if (user == null) throw new NotFoundException("OTP_NOT_FOUND");
        if (user.getOtpExpiredDate().isBefore(LocalDateTime.now())) throw new TokenExpiredException("TOKEN_EXPIRED");
        return user;
    }

    @Override
    public void deleteResetPasswordToken(User user) {
        user.setOtp(null);
        user.setOtpExpiredDate(null);
    }
}
