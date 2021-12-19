package id.careerfund.api.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Table(name = "users_classes")
@Entity
@Setter
@Getter
@NoArgsConstructor
public class UserClass extends Auditable {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(optional = false)
    @JoinColumn(name = "class_id", nullable = false)
    private Class aClass;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    @Column(name = "score")
    private Double score;

    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "transfered_to_bootcamp_id")
    private FinancialTransaction transferredToBootcamp;
}