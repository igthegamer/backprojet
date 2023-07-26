package work.example.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import work.example.demo.Model.Realisation;

import java.io.IOException;
import java.util.List;

@Service
public interface RealisationService {
    Realisation createRealisation(Realisation realisation);
    List<Realisation> getAllRealisations();
    Realisation getRealisationById(String id);
    Realisation updateRealisation(Realisation realisation);
    void deleteRealisation(String id);
    String uploadImage(String id, MultipartFile file) throws IOException;
    byte[] getPhoto(String id) throws Exception;
    List<Realisation> getRealisationsProById(String idProf);
}
