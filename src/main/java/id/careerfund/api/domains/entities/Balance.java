package id.careerfund.api.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "balance")
public class Balance extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nominal", nullable = false)
    private Double nominal = 0.0;

    @JsonIgnore
    @OneToOne(mappedBy = "balance", orphanRemoval = true)
    private User user;

    @JsonIgnore
    @OneToOne(mappedBy = "balance", orphanRemoval = true)
    private Institution institution;

    @OneToMany(mappedBy = "balance", orphanRemoval = true)
    private List<BalanceHistory> balanceHistories = new ArrayList<>();

}