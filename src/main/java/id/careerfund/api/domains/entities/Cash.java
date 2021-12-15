package id.careerfund.api.domains.entities;

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
@Table(name = "cash")
public class Cash extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "change", nullable = false)
    private Double change;

    @Column(name = "current", nullable = false)
    private Double current = 0.0;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "financial_transaction_id")
    private FinancialTransaction financialTransaction;
}