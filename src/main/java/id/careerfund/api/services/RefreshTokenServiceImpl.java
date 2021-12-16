package id.careerfund.api.services;

import java.time.Instant;
import java.util.UUID;

import id.careerfund.api.domains.entities.RefreshToken;
import id.careerfund.api.domains.models.requests.SignOutRequest;
import id.careerfund.api.repositories.RefreshTokenRepository;
import id.careerfund.api.repositories.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
    public RefreshToken findByToken(String refreshToken) {
        return refreshTokenRepo.findByToken(refreshToken);
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
    public void deleteByToken(String token) {
        refreshTokenRepo.deleteByTokenEquals(token);
    }

    @Override
    public void signOut(SignOutRequest signOutRequest) throws Exception {
        String token = signOutRequest.getRefreshToken();

        if (ObjectUtils.isEmpty(token)) {
            throw new Exception("Bad Request");
        }
        if (ObjectUtils.isEmpty(findByToken(token))) {
            throw new NotFoundException("Token Not Found");
        }

        try {
            deleteByToken(token);
        } catch (Exception e) {
            throw new Exception("Bad Request");
        }
    }
}
