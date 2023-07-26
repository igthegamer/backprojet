package work.example.demo.services.imp;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import work.example.demo.Model.Professional;
import work.example.demo.Model.Project;
import work.example.demo.Model.Realisation;
import work.example.demo.Repository.ProfessionalRepository;
import work.example.demo.Repository.RealisationRepository;
import work.example.demo.entities.ProfessionalEntity;
import work.example.demo.entities.RealisationEntity;
import work.example.demo.services.RealisationService;
import work.example.demo.services.imp.SessionServiceImpl;
import work.example.demo.services.imp.UploadServiceImpl;
import work.example.demo.handler.RessourceNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RealisationServiceImpl implements RealisationService {
    public  static  String  uploadDirectory=System.getProperty("user.dir")+"/src/main/webapp/Images/";
    private final RealisationRepository realisationRepository;
    private final SessionServiceImpl sessionService;
    private final UploadServiceImpl uploadService;
    private final ProfessionalRepository professionalRepository;

    public RealisationServiceImpl(RealisationRepository realisationRepository, SessionServiceImpl sessionService,
                                  UploadServiceImpl uploadService, ProfessionalRepository professionalRepository) {
        this.realisationRepository = realisationRepository;
        this.sessionService = sessionService;
        this.uploadService = uploadService;
        this.professionalRepository = professionalRepository;
    }
    @Override
    public Realisation createRealisation(Realisation realisation) {
        return Realisation.toModel(realisationRepository.save(Realisation.toEntity(realisation)));
    }

    @Override
    public List<Realisation> getAllRealisations() {
        List<RealisationEntity> realisations=new ArrayList<RealisationEntity>();
        realisations.addAll(realisationRepository.findAll());
        return Realisation.convertRealisationEntityListToRealisationList(realisations);
    }
    @Override
    public Realisation getRealisationById(String id) {
        Optional<RealisationEntity> realisation = realisationRepository.findById(id);
        if (realisation.isPresent()){
            return Realisation.toModel(realisation.get());
        }else {
            throw new RessourceNotFoundException("Realisation","id",id);
        }
    }
    @Override
    public Realisation updateRealisation(Realisation realisation) {
        return Realisation.toModel(realisationRepository.save(Realisation.toEntity(realisation)));
    }
    @Override
    public void deleteRealisation(String id) {
        realisationRepository.deleteById(id);
    }
    @Override
    public String uploadImage(String id, MultipartFile file) throws IOException {
        RealisationEntity realisation = realisationRepository.findById(id).get();
        String image = this.uploadService.uploadImage(file);
        realisation.getImages().add(image);
        realisationRepository.save(realisation);
        return image;
    }
    @Override
    public byte[] getPhoto(String id) throws Exception{
        RealisationEntity realisation   = realisationRepository.findById(id).get();
        return Files.readAllBytes(Paths.get(uploadDirectory+realisation.getImages()));
    }
    @Override
    public List<Realisation> getRealisationsProById(@PathVariable("idProf") String idProf) {
        Optional<ProfessionalEntity> professionalOptional = Optional.ofNullable(professionalRepository.findById(idProf));
        ProfessionalEntity professional = null;
        try {
            professional = professionalOptional.orElseThrow(() -> new Exception("Professional not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<RealisationEntity> realisations = realisationRepository.findByProfessional(professional);
        return Realisation.convertRealisationEntityListToRealisationList(realisations);
    }


}

