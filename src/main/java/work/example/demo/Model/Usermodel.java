package work.example.demo.Model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import work.example.demo.entities.User;
import work.example.demo.entities.UserRole;

import javax.persistence.Column;

@Builder
@Getter
@Setter
public class Usermodel {

private String id;
private String password;
private String email;
private String firstName;
private String lastName;
    @Column(nullable = false, columnDefinition = "boolean default false")

private Boolean enabled = false;
private String address;
private UserRole UserRole;
    @Column(nullable = false, columnDefinition = "boolean default false")

private Boolean locked=false;

    public static User toEntity(User user) {
        return User.builder().id(user.getId()).firstName(user.getFirstName()).lastName(user.getLastName()).
                email(user.getEmail())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .address(user.getAddress())
                .locked(user.isAccountNonLocked())
                .UserRole(user.getUserRole())
                .build();
    }
    public static User toModel(User user) {
        if(user==null)
            return null;
        return User.builder().id(user.getId()).firstName(user.getFirstName()).lastName(user.getLastName()).
                email(user.getEmail())
                .password(user.getPassword())
                .address(user.getAddress())
                .phone_number(user.getPhone_number())
                .enabled(user.isEnabled())
                .locked(user.isAccountNonLocked())
                .UserRole(user.getUserRole())
                .build();
    }


}

