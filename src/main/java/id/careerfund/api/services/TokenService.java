package id.careerfund.api.services;

import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.NewTokenRequest;
import id.careerfund.api.domains.models.SignInRequest;
import id.careerfund.api.domains.models.TokenResponse;
import org.springframework.security.authentication.DisabledException;

import java.util.List;

public interface TokenService {
    Boolean verifyUser(String email);

    void authUser(String email, String password) throws DisabledException;

    String refreshToken(String email);

    TokenResponse getNewToken(NewTokenRequest request) throws Exception;

    TokenResponse getToken(User user);
}
