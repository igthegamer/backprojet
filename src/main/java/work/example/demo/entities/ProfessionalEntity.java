package work.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "professionals")
public class ProfessionalEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 40)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String firstName;
    private String lastName;
    private String phone_number;
    private String email;
    @ElementCollection
    @CollectionTable(name = "professional_work_places", joinColumns = @JoinColumn(name = "professional_id"))
    @Column(name = "work_place")
    private List<String> workPlaces;

    @ElementCollection
    @CollectionTable(name = "professional_work_types", joinColumns = @JoinColumn(name = "professional_id"))
    @Column(name = "work_type")
    private List<String> workTypes;

    @OneToMany(mappedBy = "professional")
    private List<ProjectEntity> projects;

    @OneToMany(mappedBy = "sender")
    private List<ProposalEntity> sentProposals;

}


