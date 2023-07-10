package work.example.demo.services;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.example.demo.Repository.ProfessionalRepository;
import work.example.demo.entities.*;
import work.example.demo.utilities.EmailValidator;
import work.example.demo.templates.EmailTemplate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class registrationService {
    private final userservice Userservice;
    private EmailValidator emailValidator;
    private final tokenservice tokenService;
    private final EmailSender emailSender;


    public String register(RegistrationRequest request) {
        boolean isValidEmail=emailValidator.test((request.getEmail()));
        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        UserRole userRole = roleservice.getUserRole(request.getRole());
        String token  = Userservice.signUpUser(
                new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                request.getAddress(),
                request.getPhoneNumber(),
                userRole         )
        );
        if (userRole == UserRole.Pro) {
            ProfessionalEntity professionalEntity = ProfessionalEntity.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .build();

            professionalRepository.save(professionalEntity);
        }

        String link = "http://localhost:8081/api/v1/registration/confirm?token=" + token;
        emailSender.send(
                request.getEmail(),
                emailTemplate.buildEmail(request.getFirstName(), link));

        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        token  confirmationToken = tokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));
        System.out.println("Token value: " + token);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        System.out.println("Token expiration: " + expiredAt);

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");

        }
        System.out.println("User email: " + confirmationToken.getUser().getEmail());

        tokenService.setConfirmedAt(token);
        Userservice.enableUser(
                confirmationToken.getUser().getEmail());
        System.out.println("confirmToken method completed successfully");

        return "confirmed";
    }


    @Autowired
    private EmailTemplate emailTemplate;
    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private Roleservice roleservice;


}

