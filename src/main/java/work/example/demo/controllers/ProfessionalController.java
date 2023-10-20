package work.example.demo.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.example.demo.Model.Professional;
import work.example.demo.Repository.ProfessionalRepository;
import work.example.demo.entities.ProfessionalEntity;
import work.example.demo.entities.User;
import work.example.demo.services.ProfessionalService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/professionals")
@CrossOrigin(origins = "http://localhost:4200/**")

class ProfessionalController {
    private final ProfessionalService professionalService;
    private final ProfessionalRepository professionalRepository;
    public ProfessionalController(ProfessionalService professionalService,ProfessionalRepository professionalRepository) {
        this.professionalService = professionalService;
        this.professionalRepository=professionalRepository;
    }
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody User professional) throws Exception {
        return professionalService.create(professional);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProfessionalEntity>> searchProjects(@RequestParam List<String> keyword) {
        List<ProfessionalEntity> projects = new ArrayList<>();

        projects.addAll(professionalRepository.findByFirstName(keyword));
        projects.addAll(professionalRepository.searchByWorkPlaces( keyword));
        projects.addAll(professionalRepository.findByLastName(keyword));


        return ResponseEntity.ok(projects);
    }

    @PutMapping()
    public ResponseEntity<Professional> updateProfessional( @RequestBody Professional professional){
        return new ResponseEntity<Professional> (professionalService.updateProfessional(professional), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public Professional getUserById(@PathVariable("id") String id){
        return professionalService.getUserById(id);
    }
    @GetMapping
    public List<Professional> getAllProfessionals(){
        return professionalService.getAllProfessionals();
    }
    @DeleteMapping("{id}")
    public  ResponseEntity<String> deleteProject(@PathVariable("id") Number id){
        professionalService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}