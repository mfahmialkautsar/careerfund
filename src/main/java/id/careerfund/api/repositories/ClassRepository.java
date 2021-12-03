package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Class;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Class, Long> {
}
