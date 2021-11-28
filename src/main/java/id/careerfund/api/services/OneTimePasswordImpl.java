package id.careerfund.api.services;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.bastiaanjansen.otp.HMACAlgorithm;
import com.bastiaanjansen.otp.TOTPGenerator;
import id.careerfund.api.domains.entities.OneTimePassword;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.repositories.OneTimePasswordRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class OneTimePasswordImpl implements OneTimePasswordService {
    private final OneTimePasswordRepository oneTimePasswordRepo;
    private final Environment environment;
    private TOTPGenerator totpGenerator;

    private TOTPGenerator getGenerator() {
        if (totpGenerator != null) return totpGenerator;
        TOTPGenerator.Builder builder = new TOTPGenerator
                .Builder(Objects.requireNonNull(this.environment.getProperty("totp.secret")).getBytes());

        builder.withPasswordLength(6)
                .withAlgorithm(HMACAlgorithm.SHA256)
                .withPeriod(Duration.ofMinutes(2));
        totpGenerator = builder.build();
        return totpGenerator;
    }

    @Override
    public String generateOtp(User user) {
        TOTPGenerator totp = getGenerator();
        String generatedPassword = totp.generate();
        if (oneTimePasswordRepo.findByPassword(generatedPassword) != null) return generateOtp(user);

        oneTimePasswordRepo.deleteByUser_Email(user.getEmail());

        OneTimePassword oneTimePassword = new OneTimePassword();
        oneTimePassword.setPassword(generatedPassword);
        oneTimePassword.setExpiredAt(LocalDateTime.now().plusMinutes(5));
        oneTimePassword.setUser(user);
        oneTimePasswordRepo.save(oneTimePassword);
        log.info("Generating OTP for {}", user.getEmail());
        return oneTimePassword.getPassword();
    }

    @Override
    public OneTimePassword verifyOtp(String otp) throws NotFoundException {
        log.info(String.valueOf(getGenerator().verify(otp)));
        OneTimePassword oneTimePassword = oneTimePasswordRepo.findByPassword(otp);
        if (oneTimePassword == null) throw new NotFoundException("OTP_NOT_FOUND");
        if (oneTimePassword.getExpiredAt().isBefore(LocalDateTime.now()))
            throw new TokenExpiredException("OTP_EXPIRED");

        oneTimePasswordRepo.delete(oneTimePassword);
        log.info("Verifying OTP {}", otp);
        log.info(String.valueOf(getGenerator().verify(otp)));
        return oneTimePassword;
    }
}
