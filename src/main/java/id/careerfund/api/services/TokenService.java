package id.careerfund.api.services;

import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.NewTokenRequest;
import id.careerfund.api.domains.models.SignInRequest;
import id.careerfund.api.domains.models.TokenResponse;

import java.util.List;

public interface TokenService {
    Boolean verifyUser(String email);

    Boolean authUser(String email, String password);

    List<String> roles(String email) throws Exception;

    String refreshToken(String email);

    TokenResponse signIn(SignInRequest signInRequest) throws Exception;

    TokenResponse getNewToken(NewTokenRequest request) throws Exception;

    TokenResponse getToken(User user);
}
