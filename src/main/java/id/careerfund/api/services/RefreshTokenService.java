package id.careerfund.api.services;

import id.careerfund.api.domains.entities.RefreshToken;

public interface RefreshTokenService {
    RefreshToken findByToken(String token);

    RefreshToken createRefreshToken(Long userId);

    Boolean verifyExpiration(RefreshToken token);

    String deleteByToken(String token);
}
