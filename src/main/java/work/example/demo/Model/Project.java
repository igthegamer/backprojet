package work.example.demo.Model;


import work.example.demo.entities.ProjectEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import work.example.demo.entities.User;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class Project {
    private String id;
    private String title;
    private List<String> cities;
    private List<String> workTypes;
    private String description;
    private List<String> images;
    private String dateValid;
    private Professional professional;
    private Usermodel client;
    private List<Message> messages;
    private List<Proposal> proposals;
    public static ProjectEntity toEntity(Project project) {
        return ProjectEntity.builder().id(project.getId()).title(project.getTitle()).cities(project.getCities()).
                workTypes(project.getWorkTypes()).description(project.getDescription()).images(project.getImages()).
                dateValid(project.getDateValid())
                .messages(project.getMessages()!=null?Message.convertMessageListToMessageEntityList(project.getMessages()):null)
                .proposals(project.getProposals()!=null?Proposal.convertProposalListToProposalEntityList(project.getProposals()):null)
                .professional(project.getProfessional()!=null? Professional.toEntity(project.getProfessional()).getUser() :null)
                .build();
    }
    public static Project toModel(ProjectEntity projectEntity) {
        if(projectEntity==null)
            return null;
        return Project.builder().id(projectEntity.getId()).title(projectEntity.getTitle()).cities(projectEntity.getCities()).
                workTypes(projectEntity.getWorkTypes()).description(projectEntity.getDescription()).images(projectEntity.getImages()).
                dateValid(projectEntity.getDateValid())
                .messages(projectEntity.getMessages()!=null?Message.convertMessageEntityListToMessageList(projectEntity.getMessages()):null)
                .proposals(projectEntity.getProposals()!=null?Proposal.convertProposalEntityListToProposalList(projectEntity.getProposals()):null)
                .professional(projectEntity.getProfessional()!=null?Professional.toModel(projectEntity.getProfessional().getProfessional()):null)
                .build();
    }
    public static List<ProjectEntity> convertProjectListToProjectEntityList(List<Project> projects){
        List<ProjectEntity> conv= new ArrayList<ProjectEntity>();
        projects.forEach(e->{
            conv.add(Project.toEntity(e));
        });
        return conv;
    }
    public static List<Project> convertProjectEntityListToProjectList(List<ProjectEntity> projects){
        List<Project> conv= new ArrayList<Project>();
        projects.forEach(e->{
            conv.add(Project.toModel(e));
        });
        return conv;
    }
}
