package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

public interface UserRepository extends JpaRepository<User, Long> {
    @Nullable
    User findByEmail(String email);
}