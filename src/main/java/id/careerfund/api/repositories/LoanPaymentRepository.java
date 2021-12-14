package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanPaymentRepository extends JpaRepository<LoanPayment, Long> {
}