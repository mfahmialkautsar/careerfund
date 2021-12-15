package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Funding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingRepository extends JpaRepository<Funding, Long> {
}