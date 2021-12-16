package id.careerfund.api.services;

import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.requests.NewTokenRequest;
import id.careerfund.api.domains.models.responses.TokenResponse;

public interface TokenService {
    Boolean verifyUser(String email);

    Exception authUser(String email, String password);

    String refreshToken(String email);

    TokenResponse getNewToken(NewTokenRequest request) throws Exception;

    TokenResponse getToken(User user);
}
