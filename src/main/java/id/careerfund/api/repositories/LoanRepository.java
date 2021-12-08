package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}