package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.UserBootcamp;
import id.careerfund.api.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBootcampRepository extends JpaRepository<UserBootcamp, Long> {
    List<UserBootcamp> findByUser(User user);
}