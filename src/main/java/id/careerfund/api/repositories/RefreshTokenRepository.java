package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findById(Long id);

    RefreshToken findByToken(String token);

    String deleteByTokenEquals(String token);
}
