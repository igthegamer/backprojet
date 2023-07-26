package work.example.demo.entities;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class ProjectEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length = 40)
    private String id;
    private String title;
    @ElementCollection(targetClass=String.class)
    private List<String> cities;
    @ElementCollection(targetClass=String.class)
    private List <String> workTypes;
    private String description;
    @ElementCollection(targetClass=String.class)
    private List<String> images;
    private String dateValid;
    @ManyToOne
    private User professional;
    @OneToOne
    private User client;

    @OneToMany
    private List<MessageEntity> messages = new ArrayList<MessageEntity>();
    @OneToMany
    private List<ProposalEntity> proposals = new ArrayList<ProposalEntity>();


}