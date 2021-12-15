package id.careerfund.api.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import id.careerfund.api.domains.EBalanceChange;
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
@Table(name = "balance_histories")
public class BalanceHistory extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nominal", nullable = false)
    private Double nominal;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "balance_id", nullable = false)
    private Balance balance;

    @JsonIgnore
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "financial_transaction_id")
    private FinancialTransaction financialTransaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "balance_change_type")
    private EBalanceChange balanceChangeType;

}