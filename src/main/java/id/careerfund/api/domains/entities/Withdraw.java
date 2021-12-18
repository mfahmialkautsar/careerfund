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
@Table(name = "withdraw")
public class Withdraw extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "funding_id", nullable = false)
    private Funding funding;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @Column(name = "account_number", nullable = false)
    private Long accountNumber;
}