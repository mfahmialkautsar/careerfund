package id.careerfund.api.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "loans")
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Loan extends Auditable {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "borrower_id", nullable = false)
    private User borrower;

    @Column(name = "interest_percent", nullable = false)
    private Double interestPercent;

    @Column(name = "interest_number")
    private Long interestNumber;

    @Column(name = "tenor", nullable = false)
    private Integer tenorMonth;

    @Column(name = "down_payment", nullable = false)
    private Long downPayment;

    @Column(name = "total_payment")
    private Long totalPayment;

    @Column(name = "monthly_payment", nullable = false)
    private Long monthlyPayment;

    @Column(name = "monthly_fee", nullable = false)
    private Long monthlyFee;

    @Column(name = "fee", nullable = false)
    private Long fee;

    @OneToMany(mappedBy = "loan", orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(mappedBy = "loan", orphanRemoval = true)
    private UserClass userClass;

//    @OneToMany(mappedBy = "loan", orphanRemoval = true)
//    private List<Funding> fundings = new ArrayList<>();

}