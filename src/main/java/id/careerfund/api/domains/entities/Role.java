package id.careerfund.api.domains.entities;

import id.careerfund.api.domains.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Table(name = "roles")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends Auditable<Long> implements GrantedAuthority {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private ERole name;

    @Override
    public String getAuthority() {
        return name.name();
    }
}