package id.careerfund.api.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payment_account_id")
    private PaymentAccount paymentAccount;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @OneToOne(mappedBy = "payment", orphanRemoval = true)
    private LoanPayment loanPayment;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "financial_transaction_id", nullable = false)
    private FinancialTransaction financialTransaction;

}