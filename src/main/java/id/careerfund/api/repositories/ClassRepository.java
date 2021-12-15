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
    @Query("select distinct c from Class c left join c.bootcamp.categories categories left join c.bootcamp.institutions institutions where ((:categories) is null or categories.id in (:categories)) and ((:institutions) is null or institutions.id in (:institutions)) and (upper(c.bootcamp.name) like upper(concat('%', concat(:name, '%') )) or upper(c.bootcamp.name) like upper(concat('%', concat(:name, '%') )) or upper(institutions.name) like upper(concat('%', concat(:name, '%') ))) and (:priceStart is null or c.price >= :priceStart) and (:priceEnd is null or c.price <= :priceEnd)")
    Page<Class> findDistinctByBootcamp_Categories_IdInAndBootcamp_Institutions_IdInAndBootcamp_NameIsLikeIgnoreCaseOrBootcamp_NameIsLikeIgnoreCaseOrInstitutions_NameIsLikeIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(@Param("categories") @Nullable Collection<Long> categories, @Param("institutions") @Nullable Collection<Long> institutions, @Param("name") @Nullable String name, @Param("priceStart") @Nullable Double priceStart, @Param("priceEnd") @Nullable Double priceEnd, Pageable pageable);
}
