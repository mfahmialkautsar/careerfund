package id.careerfund.api.services;

import com.auth0.jwt.exceptions.TokenExpiredException;
import id.careerfund.api.domains.entities.OneTimePassword;
import id.careerfund.api.domains.entities.User;
import javassist.NotFoundException;

public interface OneTimePasswordService {
    String generateOtp(User user);

    OneTimePassword verifyOtp(String otp) throws NotFoundException, TokenExpiredException;
}
