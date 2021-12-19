package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Funding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

public interface FundingRepository extends JpaRepository<Funding, Long> {
    @Nullable
    Funding findByIdAndLender_Id(Long id, Long lenderId);

    @Query("select distinct f from Funding f where f.lender.id = :id")
    Page<Funding> findDistinctByLender_Id(@Param("id") Long id, Pageable pageable);
}