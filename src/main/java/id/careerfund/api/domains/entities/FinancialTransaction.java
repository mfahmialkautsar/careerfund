package id.careerfund.api.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "financial_transactions")
public class FinancialTransaction extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nominal", nullable = false)
    private Double nominal;

    @JsonIgnore
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "financial_transaction_id")
    private FinancialTransaction financialTransaction;

    @JsonIgnore
    @OneToOne(mappedBy = "financialTransaction", orphanRemoval = true)
    private Payment payment;

    @JsonIgnore
    @OneToOne(mappedBy = "financialTransaction", orphanRemoval = true)
    private Funding funding;

    @JsonIgnore
    @OneToOne(mappedBy = "financialTransaction", orphanRemoval = true)
    private BalanceHistory balanceHistory;

    @JsonIgnore
    @OneToOne(mappedBy = "transferedToBootcamp", orphanRemoval = true)
    private UserClass userClass;
}