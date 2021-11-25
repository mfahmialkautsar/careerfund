package id.careerfund.api.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Entity(name = "bootcamps")
@NoArgsConstructor
@Table
public class Bootcamp extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(unique = true)
    private String icon;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bootcamp")
    private List<Class> aClasses;
}