package work.example.demo.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import work.example.demo.entities.UserRole;
@Service
@AllArgsConstructor

public class Roleservice {
    public UserRole getUserRole(String role) {
        if (role.equalsIgnoreCase("client")) {
            return UserRole.Client;
        } else if (role.equalsIgnoreCase("professional")) {
            return UserRole.Pro;
        }

        return UserRole.Client;
    }
}
