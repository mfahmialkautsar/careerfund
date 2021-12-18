package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Funding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

public interface FundingRepository extends JpaRepository<Funding, Long> {
    @Nullable
    Funding findByIdAndLender_Id(Long id, Long lenderId);
}