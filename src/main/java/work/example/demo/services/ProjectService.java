package work.example.demo.services;

import work.example.demo.Model.Project;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ProjectService {
    Project creerProject(Project project);
    List<Project> getAllProjects();
    Project getProjectById(String id);
    Project updateProject(Project project);
    void deleteProject(String id);

    String uploadImage(String id, MultipartFile file) throws IOException;

    Project addProjectToPro(Project project) throws Exception;

    Project addProjectToSender(String id, Project project);
}
