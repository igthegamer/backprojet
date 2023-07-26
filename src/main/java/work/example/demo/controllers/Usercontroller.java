package work.example.demo.controllers;
import io.jsonwebtoken.security.Keys;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import work.example.demo.Repository.UserRepo;
import work.example.demo.entities.User;
import work.example.demo.services.imp.registrationService;
import work.example.demo.entities.RegistrationRequest;
import work.example.demo.services.imp.userservice;

import java.security.Key;
import java.util.*;

@RestController
@RequestMapping(path ="api/v1/registration/**")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/**")

public class Usercontroller {

    private registrationService registrationservice;
    private userservice Userservice;

    private UserRepo repo;
    @PostMapping()
    public String register(@RequestBody RegistrationRequest request){

        return registrationservice.register(request);
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<Object> confirm(@RequestParam("token") String token) {
        String result = registrationservice.confirmToken(token);
        if (result.equals("confirmed")) {
            String loginPageUrl = "http://localhost:4200/login"; // Update with your actual login page URL
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, loginPageUrl)
                    .build();
        } else {
            return ResponseEntity.badRequest().body("Confirmation failed");
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User user) {
        user.setEmail(email);
        List<String> workPlaces = user.getWorkPlaces();
        if (workPlaces != null) {
            user.setWorkPlaces(workPlaces);
        }
        User updatedUser = Userservice.updateUser(email,user); // Use the injected Userservice
        return ResponseEntity.ok(updatedUser);
    }


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<User> optionalUser = repo.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (!user.isEnabled()) {
                return ResponseEntity.badRequest().body("User account is not confirmed. Please confirm your registration.");
            }

            if (passwordEncoder.matches(password, user.getPassword())) {
                String userRole = user.getRole().name(); // Convert the UserRole enum to a string

                String redirectUrl = userRole.equals("Client") ? "/client" : "/pro";

                String jwtToken = generateJwtToken(email, userRole);

                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("token", jwtToken);
                responseBody.put("redirectUrl", redirectUrl);
                responseBody.put("role", userRole);
                responseBody.put("user", user); // Add the user data to the response

                return ResponseEntity.ok().body(responseBody);
            } else {
                return ResponseEntity.badRequest().body("Invalid password");
            }
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    public String generateJwtToken(String userEmail, String userRole) {
        Date expiration = new Date(System.currentTimeMillis() + 3600000);

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String token = Jwts.builder()
                .setSubject(userEmail)
                .claim("role", userRole)
                .setExpiration(expiration)
                .signWith(key)
                .compact();

        return token;
    }

    @GetMapping("/email")
    public String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return userEmail;
    }





    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }


}
