package id.careerfund.api.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "loan_payments")
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LoanPayment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    @Column(name = "period", nullable = false)
    private Integer period;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    // @ManyToOne(optional = false)
    // @JoinColumn(name = "payment_account_id", nullable = false)
    // private PaymentAccount paymentAccount;

}