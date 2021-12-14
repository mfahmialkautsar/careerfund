package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.BalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceHistoryRepository extends JpaRepository<BalanceHistory, Long> {
}