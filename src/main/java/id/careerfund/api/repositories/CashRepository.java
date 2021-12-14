package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Cash;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashRepository extends JpaRepository<Cash, Long> {
    Cash findFirstByOrderByCreatedAtDesc();
}