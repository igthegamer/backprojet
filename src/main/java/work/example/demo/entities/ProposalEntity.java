package work.example.demo.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "proposals")
public class ProposalEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length = 40)
    private String id;
    private Boolean approved;
    @ManyToOne
    private User receiver;
    @ManyToOne
    private ProfessionalEntity sender;
    @OneToOne
    private ProjectEntity project;

}
