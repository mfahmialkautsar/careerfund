package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}