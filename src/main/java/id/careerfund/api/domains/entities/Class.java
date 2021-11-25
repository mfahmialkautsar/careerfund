package id.careerfund.api.domains.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity(name = "classes")
@NoArgsConstructor
@Table
public class Class extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private Interest interest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private Bootcamp bootcamp;

    @Column
    private int duration;

    @Column
    private int quota;

    @Column
    private int fee;
}
