package id.careerfund.api.domains.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "bootcamps")
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bootcamp extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "logo_path")
    private String logoPath;

    @ManyToMany
    @JoinTable(name = "bootcamps_institutions",
            joinColumns = @JoinColumn(name = "bootcamp_id"),
            inverseJoinColumns = @JoinColumn(name = "institution_id"))
    private List<Institution> institutions = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "bootcamps_categories",
            joinColumns = @JoinColumn(name = "bootcamp_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Interest> categories = new ArrayList<>();
}