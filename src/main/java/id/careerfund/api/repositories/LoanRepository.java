package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Page<Loan> findDistinctByFundings_IdIs(Long id, Pageable pageable);
    Page<Loan> findDistinctByLoanPaymentsNotEmpty(Pageable pageable);
}