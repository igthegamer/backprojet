package work.example.demo.services.imp;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import work.example.demo.Model.Usermodel;
import work.example.demo.Repository.UserRepo;
import work.example.demo.entities.User;
import work.example.demo.entities.ProfessionalEntity;
import work.example.demo.entities.ProjectEntity;
import work.example.demo.handler.RessourceNotFoundException;
import work.example.demo.Model.Message;
import work.example.demo.Model.Professional;
import work.example.demo.Model.Project;
import work.example.demo.Repository.ProfessionalRepository;
import work.example.demo.Repository.ProjectRepository;
import work.example.demo.services.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    private final ProfessionalRepository professionalRepository;
    private final UserRepo clientRepository;
    private final SessionServiceImpl sessionService;
    private final UploadServiceImpl uploadService;
    public  static  String  uploadDirectory=System.getProperty("user.dir")+"/src/main/webapp/Images/";


    public ProjectServiceImpl( ProjectRepository projectRepository, ProfessionalRepository professionalRepository, UserRepo clientRepository, SessionServiceImpl sessionService, UploadServiceImpl uploadService) {
        this.projectRepository = projectRepository;
        this.professionalRepository = professionalRepository;
        this.clientRepository = clientRepository;
        this.sessionService = sessionService;
        this.uploadService = uploadService;

    }
    @Override
    public Project creerProject(Project project) {
        return Project.toModel(projectRepository.save(Project.toEntity(project)));
    }

    @Override
    public List<Project> getAllProjects() {
        List<ProjectEntity> projects=new ArrayList<ProjectEntity>();
        projectRepository.findAll().forEach(element->projects.add(element));
        return Project.convertProjectEntityListToProjectList(projects);
    }
    @Override
    public Project getProjectById(String id) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(id);
        if (projectEntity.isPresent()){
            return Project.toModel(projectEntity.get());
        }else {
            throw new RessourceNotFoundException("Project","id",id);
        }
    }
    @Override
    public Project updateProject(Project project) {
        //checking if the product exist in DB or not
        ProjectEntity existingProject = projectRepository.findById(project.getId()).orElseThrow(() -> new RessourceNotFoundException("Project","id",project.getId()));
        existingProject.setTitle(project.getTitle());
        existingProject.setDescription(project.getDescription());
        existingProject.setCities(project.getCities());
        existingProject.setDateValid(project.getDateValid());
        existingProject.setWorkTypes(project.getWorkTypes());
        existingProject.setImages(project.getImages());
        existingProject.setClient(Usermodel.toEntity(project.getClient()));
        existingProject.setProfessional(Professional.toEntity(project.getProfessional()).getUser());
        existingProject.setMessages(Message.convertMessageListToMessageEntityList(project.getMessages()));
        //saving
        return Project.toModel(projectRepository.save(existingProject));
    }

    @Override
    public void deleteProject(String id) {
        //checking if the product exist in DB or not
        projectRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("Project","id",id));
        projectRepository.deleteById(id);
    }

    @Override
    public String uploadImage(String id, MultipartFile file) throws IOException {
        ProjectEntity projectEntity = projectRepository.findById(id).get();


        String image = this.uploadService.uploadImage(file);
        projectEntity.getImages().add(image);
        projectRepository.save(projectEntity);
        return image;


    }


    @Override
    public Project addProjectToPro(Project project) {
        ProfessionalEntity p = sessionService.getUserConnected().getProfessional();
        project.setProfessional(Professional.toModel(p));
        return Project.toModel(projectRepository.save(Project.toEntity(project)));
    }
    @Override
    public Project addProjectToSender(String id,Project project) {
        ProfessionalEntity p = professionalRepository.findById(id);
        project.setProfessional(Professional.toModel(p));
        return Project.toModel(projectRepository.save(Project.toEntity(project)));
    }

}
