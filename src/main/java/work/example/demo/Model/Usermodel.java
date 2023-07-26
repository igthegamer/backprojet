package work.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import work.example.demo.entities.ProfessionalEntity;
import work.example.demo.entities.User;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Usermodel {
    private String id;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phone_number;
    private boolean enabled;
    private ProfessionalEntity professional;
    private List<Project> projects;
    private List<Proposal> receivedProposals;

    public static User toEntity(Usermodel user) {
        return User.builder()
                .id(Long.valueOf(user.getId()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .phone_number(user.getPhone_number())
                .projects(Project.convertProjectListToProjectEntityList(user.getProjects()))
                .receivedProposals(Proposal.convertProposalListToProposalEntityList(user.getReceivedProposals()))

                .professional(user.getProfessional())
                .build();
    }

    public static Usermodel toModel(User user) {
        if (user == null) {
            return null;
        }
        return Usermodel.builder()
                .id(user.getId().toString())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phone_number(user.getPhone_number())
                .enabled(user.isEnabled())
                .professional(user.getProfessional())
                .build();
    }
}


