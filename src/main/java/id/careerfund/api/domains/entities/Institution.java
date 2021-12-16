package id.careerfund.api.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "institutions")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Institution extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Lob
    @Column(name = "logo_path")
    private String logoPath;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "bootcamps_institutions", joinColumns = @JoinColumn(name = "institution_id"), inverseJoinColumns = @JoinColumn(name = "bootcamp_id"))
    private List<Bootcamp> bootcamps = new ArrayList<>();

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "balance_id")
    private Balance balance;

}