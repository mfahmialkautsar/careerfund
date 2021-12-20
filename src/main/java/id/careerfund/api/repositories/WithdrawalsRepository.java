package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Withdrawals;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Collection;

public interface WithdrawalsRepository extends JpaRepository<Withdrawals, Long> {
    @Query("select distinct w from Withdrawals w where w.funding.lender.id = :userId and (cast(:createdAts as timestamp) is null or w.createdAt in (:createdAts))")
    Page<Withdrawals> findDistinctByFunding_Lender_IdAndCreatedAtIn(@Param("userId") Long userId,
            @Param("createdAts") @Nullable Collection<LocalDateTime> createdAts, Pageable pageable);

    @Nullable
    Withdrawals findByFunding_Lender_IdAndId(Long userId, Long withdrawalId);
}