package work.example.demo.services.imp;


import work.example.demo.entities.ProfessionalEntity;
import work.example.demo.entities.User;
import work.example.demo.Model.Professional;
import work.example.demo.Repository.ProfessionalRepository;
import work.example.demo.Repository.UserRepo;
import work.example.demo.handler.RessourceNotFoundException;
import work.example.demo.services.ProfessionalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProfessionalServiceImpl implements ProfessionalService {
    private final ProfessionalRepository professionalRepository;
    private final SessionServiceImpl sessionService;
    private final UserRepo userRepository;
    public ProfessionalServiceImpl(ProfessionalRepository professionalRepository, SessionServiceImpl sessionService, UserRepo userRepository) {
        this.professionalRepository = professionalRepository;
        this.sessionService = sessionService;
        this.userRepository = userRepository;
    }
    @Transactional
    @Override
    public ResponseEntity<Object> create(User professional) throws Exception {
        User userConnected = sessionService.getUserConnected();

        ProfessionalEntity professionalExist = userConnected.getProfessional();
        if (professionalExist != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already having a Professional account");
        } else {
            ProfessionalEntity professionalEntity = ProfessionalEntity.builder()
                    .firstName(professional.getFirstName())
                    .lastName(professional.getLastName())
                    .phone_number(professional.getPhone_number())

                    .build();

            ProfessionalEntity savedProfessionalEntity = professionalRepository.save(professionalEntity);

            if (userConnected != null) {
                userConnected.setProfessional(savedProfessionalEntity);
                userRepository.save(userConnected);
            }

            return ResponseEntity.ok().body(Professional.toModel(savedProfessionalEntity));
        }
    }
    @Override
    public void deleteProject(Number id) {
        professionalRepository.findById((Long) id);
        professionalRepository.deleteById((Long) id);
    }
    @Override
    public Professional updateProfessional(Professional professional) {
        String decryptedId = decryptId(professional.getId()); // Perform decryption or hashing to get the actual ID value
        ProfessionalEntity existingProfessional = professionalRepository.findById(decryptedId);
        if (existingProfessional == null) {
            throw new IllegalArgumentException("Professional not found");
        }

        // Update the professional entity with the new values
        existingProfessional.setFirstName(professional.getFirstName());
        existingProfessional.setLastName(professional.getLastName());
        existingProfessional.setPhone_number(professional.getPhone_number());
        existingProfessional.setEmail(professional.getEmail());
        existingProfessional.setWorkPlaces(professional.getWorkPlaces());
        existingProfessional.setWorkTypes(professional.getWorkTypes());

        // Save the updated professional entity
        ProfessionalEntity updatedProfessional = professionalRepository.save(existingProfessional);

        return Professional.toModel(updatedProfessional);
    }

    private String decryptId(String encryptedId) {
        // Perform decryption or hashing operations to get the actual ID value from the encrypted ID
        // Replace this with your actual decryption/hashing logic
        return encryptedId; // Placeholder: Replace with actual decryption/hashing logic
    }
    @Override
    public List<Professional> getAllProfessionals() {
        List<ProfessionalEntity> professionalEntityList=new ArrayList<ProfessionalEntity>();
        professionalRepository.findAll().forEach(element->professionalEntityList.add(element));
        return Professional.convertProfessionalEntityListToProfessionalList(professionalEntityList);
    }
    @Override
    public Professional getUserById(String id){
        ProfessionalEntity p = professionalRepository.findById(id);
        return Professional.toModel(p);
    }

}
