package work.example.demo.services.imp;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import work.example.demo.Repository.UserRepo;
import work.example.demo.entities.User;
import work.example.demo.entities.token;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class userservice implements UserDetailsService {


    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final work.example.demo.services.imp.tokenservice tokenservice;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }
    public void updateFirstLogin(String email) {
        Optional<User> optionalUser = userRepo.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstLogin(false);
            userRepo.save(user);
        }
    }


    public User updateUser(String email, User user) {
        Optional<User> existingUserOptional = userRepo.findByEmail(email);
        if (existingUserOptional.isEmpty()) {
            throw new IllegalStateException("User not found");
        }

        User existingUser = existingUserOptional.get();
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setAddress(user.getAddress());
        existingUser.setPhone_number(user.getPhone_number());
        existingUser.setWorkPlaces(user.getWorkPlaces());

        return userRepo.save(existingUser);
    }

    public String signUpUser(User user){
        boolean userExists = userRepo.findByEmail(user.getEmail()).isPresent();
        if (userExists){
            throw  new IllegalStateException("email already taken");
        }
        String encodedPassword =bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
        String token = UUID.randomUUID().toString();
        token token1 = new token(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        tokenservice.savetoken(token1);
        return token;
    }
    public int enableUser(String email) {
        return userRepo.enableUser(email);
    }

}
