package work.example.demo.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.example.demo.Model.Professional;
import work.example.demo.entities.User;
import work.example.demo.services.ProfessionalService;

import java.util.List;

@RestController
@RequestMapping("api/v1/professionals")
@CrossOrigin(origins = "http://localhost:4200/**")

class ProfessionalController {
    private final ProfessionalService professionalService;
    public ProfessionalController(ProfessionalService professionalService) {
        this.professionalService = professionalService;
    }
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody User professional) throws Exception {
        return professionalService.create(professional);
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
}