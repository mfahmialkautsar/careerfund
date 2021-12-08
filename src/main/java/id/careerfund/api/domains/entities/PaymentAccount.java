package id.careerfund.api.domains.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "payment_account")
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAccount extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "payment_type_id", nullable = false)
    private PaymentType paymentType;

    @Column(name = "number", nullable = false)
    private Long number;

    @OneToMany(mappedBy = "paymentAccount", orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

}