package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.PaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, Long> {
}