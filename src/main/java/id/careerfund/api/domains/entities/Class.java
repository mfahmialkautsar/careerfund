package id.careerfund.api.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    private Interest interest;

    @ManyToOne
    private Bootcamp bootcamp;

    @Column
    private int duration;

    @Column
    private int quota;

    @Column
    private int fee;
}
