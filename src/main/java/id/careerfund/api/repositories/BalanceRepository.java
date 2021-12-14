package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
}