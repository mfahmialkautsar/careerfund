package id.careerfund.api.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "payment_accounts")
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAccount extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "payment_type_id", nullable = false)
    private PaymentType paymentType;

    @Column(name = "number", nullable = false)
    private Long number;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;
}