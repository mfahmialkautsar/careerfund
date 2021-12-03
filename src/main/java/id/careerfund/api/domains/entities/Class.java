package id.careerfund.api.domains.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "classes")
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Class extends Auditable {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "quota")
    private Integer quota;

    @Column(name = "fee")
    private Double fee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bootcamp_id", nullable = false)
    private Bootcamp bootcamp;
}
