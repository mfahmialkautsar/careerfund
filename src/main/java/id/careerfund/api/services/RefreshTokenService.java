package id.careerfund.api.services;

import id.careerfund.api.domains.entities.RefreshToken;
import id.careerfund.api.domains.models.requests.SignOutRequest;

public interface RefreshTokenService {
    RefreshToken findByToken(String refreshToken);

    RefreshToken createRefreshToken(Long userId);

    Boolean verifyExpiration(RefreshToken token);

    void deleteByToken(String token);

    void signOut(SignOutRequest signOutRequest) throws Exception;
}
