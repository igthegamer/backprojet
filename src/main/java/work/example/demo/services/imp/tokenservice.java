package work.example.demo.services.imp;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import work.example.demo.Repository.tokenrepo;
import work.example.demo.entities.token;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class tokenservice {
    private final tokenrepo tokenRepo;

    public void savetoken(token token){
        tokenRepo.save(token);
    }

    public Optional<token> getToken(String token) {
        return tokenRepo.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return tokenRepo.updateConfirmedAt(token, LocalDateTime.now());
    }

    public String generateAuthToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        String token = generateToken(userId);

        token newToken = new token(token, LocalDateTime.now(), LocalDateTime.now().plusHours(1), null);

        savetoken(newToken);

        return token;
    }

    private String generateToken(String userId) {

        return UUID.randomUUID().toString();
    }
}
