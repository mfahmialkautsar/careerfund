package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Bootcamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BootcampRepository extends JpaRepository<Bootcamp, Long> {
}
