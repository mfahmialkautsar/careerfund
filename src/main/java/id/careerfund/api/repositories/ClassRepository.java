package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Bootcamp;
import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Class, Long> {
    Class findByInterestAndBootcamp (Interest interest, Bootcamp bootcamp);
}
