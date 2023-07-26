package work.example.demo.Model;


import work.example.demo.entities.ProfessionalEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
public class Professional {
    private String id;
    private String firstName;
    private String lastName;
    private String phone_number;
    private String email;
    private List<String> workPlaces;
    private List<String> workTypes;
    private List<Project> projects;
    private List<Proposal> sentProposals;
    public static ProfessionalEntity toEntity(Professional professional) {
        return ProfessionalEntity.builder().id(professional.getId()).firstName(professional.getFirstName())
                .lastName(professional.getLastName())
                .phone_number(professional.getPhone_number())
                .workPlaces(professional.getWorkPlaces())
                .workTypes(professional.getWorkTypes())
                .email(professional.getEmail())
                .projects(Project.convertProjectListToProjectEntityList(professional.getProjects()))
                .sentProposals(Proposal.convertProposalListToProposalEntityList(professional.getSentProposals()))
                .projects(Project.convertProjectListToProjectEntityList(professional.getProjects()))
                .build();
    }
    public static Professional toModel(ProfessionalEntity professionalEntity) {
        if(professionalEntity==null)
            return null;
        return Professional.builder().id(professionalEntity.getId()).firstName(professionalEntity.getFirstName())
                .lastName(professionalEntity.getLastName()).workPlaces(professionalEntity.getWorkPlaces())
                .phone_number(professionalEntity.getPhone_number())
                .email(professionalEntity.getEmail())
                .workTypes(professionalEntity.getWorkTypes())
                .projects(Project.convertProjectEntityListToProjectList(professionalEntity.getProjects()))
                .sentProposals(Proposal.convertProposalEntityListToProposalList(professionalEntity.getSentProposals()))
                .build();
    }
    public static List<ProfessionalEntity> convertProfessionalListToProfessionalEntityList(List<Professional> professionals){
        return professionals.stream().map(Professional::toEntity).collect(Collectors.toList());
    }
    public static List<Professional> convertProfessionalEntityListToProfessionalList(List<ProfessionalEntity> professionalEntityList){
        return professionalEntityList.stream().map(Professional::toModel).collect(Collectors.toList());
    }


}
