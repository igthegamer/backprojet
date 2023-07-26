package work.example.demo.Model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import work.example.demo.entities.ProposalEntity;
import work.example.demo.entities.User;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class Proposal {
    private String id;
    private Professional sender;
    private Usermodel receiver;
    private Boolean approved;
    private Project project;


    public static ProposalEntity toEntity(Proposal proposal) {
        return ProposalEntity.builder().id(proposal.getId())
                .sender(proposal.getSender()!=null? Professional.toEntity(proposal.getSender()) :null)
                .receiver(proposal.getReceiver()!=null?Usermodel.toEntity(proposal.getReceiver()):null)
                .project(proposal.getProject()!=null?Project.toEntity(proposal.getProject()):null)
                .approved(proposal.getApproved()).build();
    }
    public static Proposal toModel(ProposalEntity proposalEntity) {
        if(proposalEntity ==null)
            return null;
        return Proposal.builder().id(proposalEntity.getId())
                .sender(proposalEntity.getSender()!=null?Professional.toModel(proposalEntity.getSender()):null)
                .receiver(proposalEntity.getReceiver()!=null?Usermodel.toModel(proposalEntity.getReceiver()):null)
                .project(proposalEntity.getProject()!=null?Project.toModel(proposalEntity.getProject()):null)
                .approved(proposalEntity.getApproved()).build();
    }
    public static List<ProposalEntity> convertProposalListToProposalEntityList(List<Proposal> proposals){
        List<ProposalEntity> conv= new ArrayList<ProposalEntity>();
        proposals.forEach(e->{
            conv.add(Proposal.toEntity(e));
        });
        return conv;
    }
    public static List<Proposal> convertProposalEntityListToProposalList(List<ProposalEntity> proposals){
        List<Proposal> conv= new ArrayList<Proposal>();
        proposals.forEach(e->{
            conv.add(Proposal.toModel(e));
        });
        return conv;
    }
}
