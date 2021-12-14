package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {
}