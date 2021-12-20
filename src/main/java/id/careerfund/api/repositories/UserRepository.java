package id.careerfund.api.repositories;

import id.careerfund.api.domains.EIdVerificationStatus;
import id.careerfund.api.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Nullable
    User findByEmail(String email);

    List<User> findByIdVerificationStatusIs(EIdVerificationStatus idVerificationStatus);

    @Nullable
    User findByOtp(String otp);
}