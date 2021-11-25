package id.careerfund.api.repositories;

import id.careerfund.api.domains.entities.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    Interest findByName(String name);
}