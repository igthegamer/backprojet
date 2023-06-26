package work.example.demo.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import work.example.demo.Repository.tokenrepo;
import work.example.demo.entities.token;

import java.time.LocalDateTime;
import java.util.Optional;

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
        return tokenRepo.updateConfirmedAt(
                token, LocalDateTime.now());
    }

}

