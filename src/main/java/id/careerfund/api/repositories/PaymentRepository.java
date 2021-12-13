package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}