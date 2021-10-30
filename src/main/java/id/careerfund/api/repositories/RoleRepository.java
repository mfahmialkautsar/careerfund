package id.careerfund.api.repositories;

import id.careerfund.api.domains.ERole;
import id.careerfund.api.domains.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}