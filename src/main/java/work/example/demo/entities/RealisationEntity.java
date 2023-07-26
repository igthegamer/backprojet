package work.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class RealisationEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length = 40)
    private String id;
    private String title;
    private String cities;
    @ElementCollection(targetClass=String.class)
    private List <String> workTypes;
    private String description;
    @ElementCollection(targetClass=String.class)
    private List<String> images;
    private String dateValid;
    @OneToOne
    private ProfessionalEntity professional;
}
