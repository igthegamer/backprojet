package work.example.demo.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import work.example.demo.entities.User;
import work.example.demo.services.registrationService;
import work.example.demo.entities.RegistrationRequest;
import work.example.demo.services.userservice;

@RestController
@RequestMapping(path ="api/v1/registration")
@AllArgsConstructor
public class Usercontroller {

    private registrationService registrationservice;
    private userservice Userservice;
    @PostMapping()
    public String register(@RequestBody RegistrationRequest request){

        return registrationservice.register(request);
    }
    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationservice.confirmToken(token);
    }
    @PostMapping(path = "login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        // Retrieve the email and password from the login form
        String email = loginForm.getUsername();
        String password = loginForm.getPassword();

        // Authenticate the user using Spring Security mechanisms (e.g., with UserDetailsService)

        // Retrieve the user details based on the authenticated email
        UserDetails userDetails = Userservice.loadUserByUsername(email);

        // Determine the role of the user and redirect accordingly
        if (userDetails != null) {
            if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Client"))) {
                return ResponseEntity.ok("/client"); // Redirect to the client page
            } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Pro"))) {
                return ResponseEntity.ok("/professional"); // Redirect to the professional page
            }
        }

        return ResponseEntity.badRequest().body("Invalid credentials"); // Handle invalid credentials case
    }

    // LoginForm class to map the login request payload
    public static class LoginForm {
        private String email;
        private String password;

        // Getters and setters

        public String getUsername() {
            return email;
        }

        public void setUsername(String username) {
            this.email = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
