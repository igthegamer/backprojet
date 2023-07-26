package work.example.demo.controllers;



import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import work.example.demo.Model.Realisation;
import work.example.demo.Repository.ProfessionalRepository;
import work.example.demo.Repository.RealisationRepository;
import work.example.demo.services.RealisationService;
import work.example.demo.services.SessionService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/realisations")
public class RealisationController {
    private final RealisationService realisationService;
    private final SessionService sessionService;

    public RealisationController(RealisationService realisationService, RealisationRepository realisationRepository, ProfessionalRepository professionalRepository,SessionService sessionService) {
        this.realisationService = realisationService;
        this.sessionService = sessionService;

    }

    @PostMapping
    public Realisation creeRealisation(@RequestBody Realisation Realisation) throws Exception , JsonMappingException, JsonParseException {

        return realisationService.createRealisation(Realisation);
    }


    @GetMapping("/ImgRealisation/{id}")
    public byte[] getPhoto(@PathVariable("id") String id) throws Exception{
        return realisationService.getPhoto(id);
    }
    @GetMapping
    public List<Realisation> getAllRealisations(){
        return realisationService.getAllRealisations();
    }
    @GetMapping("{id}")
    public ResponseEntity<Realisation> getRealisationById(@PathVariable("id") String id){
        return new ResponseEntity<Realisation>(realisationService.getRealisationById(id), HttpStatus.OK);
    }
    @PutMapping()
    public Realisation updateRealisation(@RequestBody Realisation realisation ) throws Exception {
        return realisationService.updateRealisation(realisation);
    }
    @PostMapping("/image/{id}")
    public String uploadImage(@PathVariable (value="id") String id, @RequestParam("file") MultipartFile file) throws IOException {
        return realisationService.uploadImage(id,file);
    }
    @DeleteMapping("{id}")
    public  ResponseEntity<String> deleteRealisation(@PathVariable("id") String id){
        realisationService.deleteRealisation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/professioinal/{idProf}")
    public  List<Realisation> getRealisationsProById(@PathVariable("idProf") String idProf){
        return realisationService.getRealisationsProById(idProf);
    }

}
