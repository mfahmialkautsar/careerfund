package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {
}