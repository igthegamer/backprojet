package work.example.demo.services;

import work.example.demo.Model.Professional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import work.example.demo.entities.User;

import java.util.List;

@Service
public interface ProfessionalService {
    ResponseEntity<Object> create(User professional) throws Exception;
    Professional updateProfessional(Professional professional);
    List<Professional> getAllProfessionals();
    Professional getUserById(String id);
}
