package id.careerfund.api.domains.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "one_time_passwords")
@Entity
@Getter
@Setter
public class OneTimePassword extends Auditable {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "password", nullable = false, unique = true, length = 6)
    private String password;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}