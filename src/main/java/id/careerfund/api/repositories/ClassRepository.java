package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.Collection;

public interface ClassRepository extends JpaRepository<Class, Long> {
    @Query("select c from Class c left join c.bootcamp.categories categories left join c.bootcamp.institutions institutions where ((:categories) is null or categories.id in (:categories)) and ((:institutions) is null or institutions.id in (:institutions)) and upper(c.bootcamp.name) like upper(concat('%', concat(:name, '%') )) and (:feeStart is null or c.fee >= :feeStart) and (:feeEnd is null or c.fee <= :feeEnd)")
    Page<Class> findByBootcamp_Categories_IdInAndBootcamp_Institutions_IdInAndBootcamp_NameIsLikeIgnoreCaseAndFeeGreaterThanEqualAndFeeLessThanEqual(@Param("categories") @Nullable Collection<Long> categories, @Param("institutions") @Nullable Collection<Long> institutions, @Param("name") @Nullable String name, @Param("feeStart") @Nullable Double feeStart, @Param("feeEnd") @Nullable Double feeEnd, Pageable pageable);
}
