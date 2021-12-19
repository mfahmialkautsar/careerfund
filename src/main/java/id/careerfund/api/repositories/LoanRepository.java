package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Page<Loan> findByFundings_Lender_Id(Long id, Pageable pageable);

    boolean existsByIdAndFundings_Lender_Id(Long id, Long lenderId);

    Page<Loan> findDistinctByLoanPaymentsNotEmpty(Pageable pageable);
}