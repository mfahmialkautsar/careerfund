package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.UserClass;
import id.careerfund.api.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserClassRepository extends JpaRepository<UserClass, Long> {
    List<UserClass> findByUser(User user);
}