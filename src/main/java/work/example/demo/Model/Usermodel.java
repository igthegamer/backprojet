package work.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import work.example.demo.entities.ProfessionalEntity;
import work.example.demo.entities.User;

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
    private boolean enabled;
    private String client;
    private ProfessionalEntity professional;

    public static User toEntity(Usermodel user) {
        return User.builder()
                .id(Long.valueOf(user.getId()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .client(user.getClient()) // Convert the client to User entity
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
                .enabled(user.isEnabled())
                .client(user.getClient())// Convert the client to Usermodel
                .professional(user.getProfessional())
                .build();
    }
}


