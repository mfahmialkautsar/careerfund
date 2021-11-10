package id.careerfund.api.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import id.careerfund.api.domains.entities.RefreshToken;
import id.careerfund.api.repositories.RefreshTokenRepository;
import id.careerfund.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
public class RefreshTokenService {
    @Value("${security.jwt.token_expired_days}")
    private Long refreshTokenDurationDs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationDs * 2 * 24 * 3600 * 1000));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw null;
        }
        return token;
    }

    @Transactional
    public String deleteByToken(String token) {
        try {
            return refreshTokenRepository.deleteByTokenEquals(token);
        } catch (Exception e) {return e.getMessage();}
    }
}