package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.OneTimePassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword, Long> {
    @Nullable
    OneTimePassword findByPassword(String password);

    void deleteByUser_Email(String email);
}