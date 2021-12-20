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
@Table(name = "fundings")
public class Funding extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(optional = false)
    @JoinColumn(name = "lender_id", nullable = false)
    private User lender;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "financial_transaction_id")
    private FinancialTransaction financialTransaction;

    @JsonIgnore
    @OneToOne(mappedBy = "funding", orphanRemoval = true)
    private Withdrawals withdrawals;
}