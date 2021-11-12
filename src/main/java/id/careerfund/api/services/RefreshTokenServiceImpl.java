package id.careerfund.api.services;

import java.time.Instant;
import java.util.UUID;

import id.careerfund.api.domains.entities.RefreshToken;
import id.careerfund.api.repositories.RefreshTokenRepository;
import id.careerfund.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Value("${security.jwt.token_expired_days}")
    private Long refreshTokenDurationDs;
    private final RefreshTokenRepository refreshTokenRepo;
    private final UserRepository userRepo;

    @Override
    public RefreshToken findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepo.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusSeconds(refreshTokenDurationDs * 2 * 24 * 3600));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepo.save(refreshToken);
        return refreshToken;
    }

    @Override
    public Boolean verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            return false;
        }
        return true;
    }

    @Override
    public String deleteByToken(String token) {
        return refreshTokenRepo.deleteByTokenEquals(token);
    }
}