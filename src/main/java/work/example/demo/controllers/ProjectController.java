package work.example.demo.controllers;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import work.example.demo.entities.ProfessionalEntity;
import work.example.demo.entities.ProjectEntity;
import work.example.demo.Model.Project;
import work.example.demo.Repository.ProfessionalRepository;
import work.example.demo.Repository.ProjectRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.web.bind.annotation.*;
import work.example.demo.Repository.UserRepo;
import work.example.demo.entities.User;
import work.example.demo.services.ProjectService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/projects")

public class ProjectController {
    private final ProjectRepository projectRepository;

    private final ProfessionalRepository professionalRepository;
    private final UserRepo clientRepository;
    private final ProjectService projectService;

    public  static  String  uploadDirectory=System.getProperty("user.dir")+"/src/main/webapp/Images/";
    public ProjectController( ProjectRepository projectRepository, ProfessionalRepository professionalRepository, UserRepo clientRepository, ProjectService projectService) {
        this.projectRepository = projectRepository;
        this.professionalRepository = professionalRepository;
        this.clientRepository = clientRepository;
        this.projectService = projectService;
    }
    @PostMapping
    public Project creeProject(@RequestBody Project project) throws Exception , JsonMappingException, JsonParseException {

        return projectService.creerProject(project);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProjectEntity>> searchProjects(@RequestParam String keyword) {
        List<ProjectEntity> projects = new ArrayList<>();

        projects.addAll(projectRepository.searchByTitle(keyword));
        projects.addAll(projectRepository.searchByDescription(keyword));


        return ResponseEntity.ok(projects);
    }
    @GetMapping("/Imgproject/{id}")
    public byte[] getPhoto(@PathVariable("id") String id) throws Exception{
        ProjectEntity project   = projectRepository.findById(id).get();
        return Files.readAllBytes(Paths.get(uploadDirectory+project.getImages()));
    }

    @PutMapping()
    public Project updateProject(@RequestBody Project project ) throws Exception {
        ProjectEntity pro = projectRepository.save(Project.toEntity(project));
        return Project.toModel(pro);
    }
    @GetMapping()
    public List<Project> getAllProjects(){
        return projectService.getAllProjects();
    }

    @GetMapping("{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("id") String projectID){
        return new ResponseEntity<Project>(projectService.getProjectById(projectID), HttpStatus.OK);
    }

    @PostMapping("/image/{id}")
    public String uploadImage(@PathVariable (value="id") String id, @RequestParam("file") MultipartFile file) throws IOException {
        return projectService.uploadImage(id,file);
    }
    @PutMapping("/push_to_pro")
    public Project addProjectToPro(@RequestBody Project project) throws Exception {
        return projectService.addProjectToPro(project);
    }
    @DeleteMapping("{id}")
    public  ResponseEntity<String> deleteProject(@PathVariable("id") String id){
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/client/{idClient}")
    public  List<Project> getProjectsById(@PathVariable("idClient") String idClient) {
        User client = clientRepository.findById(idClient);
        List<ProjectEntity> projects = projectRepository.findByClient(client);
        return Project.convertProjectEntityListToProjectList(projects);

    }
        @GetMapping("/professioinal/{idProf}")
    public  List<Project> getProjectsProById(@PathVariable("idProf") String idProf){
        ProfessionalEntity professional = professionalRepository.findById(idProf);
        List<ProjectEntity> projects=projectRepository.findByProfessional(professional);
        return Project.convertProjectEntityListToProjectList(projects);

    }

}

