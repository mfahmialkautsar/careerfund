package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Bootcamp;
import id.careerfund.api.domains.entities.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

public interface BootcampRepository extends JpaRepository<Bootcamp, Long> {
}
