package work.example.demo.services.imp;


import org.springframework.stereotype.Service;
import work.example.demo.Model.Proposal;
import work.example.demo.Repository.ProfessionalRepository;
import work.example.demo.Repository.ProjectRepository;
import work.example.demo.Repository.ProposalRepository;
import work.example.demo.Repository.UserRepo;
import work.example.demo.entities.ProfessionalEntity;
import work.example.demo.entities.ProjectEntity;
import work.example.demo.entities.ProposalEntity;
import work.example.demo.entities.User;
import work.example.demo.handler.RessourceNotFoundException;
import work.example.demo.services.ProposalService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProposalServiceImpl implements ProposalService {
    private final ProposalRepository proposalRepository;
    private final ProjectRepository projectRepository;
    private final UserRepo clientRepository;
    private final ProfessionalRepository professionalRepository;
    private final UserRepo userRepository;

    public ProposalServiceImpl(ProposalRepository proposalRepository, ProjectRepository projectRepository, UserRepo userRepository, UserRepo clientRepository, ProfessionalRepository professionalRepository, UserRepo userRepository1) {
        this.proposalRepository = proposalRepository;
        this.projectRepository = projectRepository;
        this.clientRepository = userRepository;
        this.professionalRepository = professionalRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Proposal addProposal(Proposal proposal) {
        return Proposal.toModel(proposalRepository.save(Proposal.toEntity(proposal)));
    }
    @Override
    public List<Proposal> getProposalsBySenderId(String id) {
        List<ProposalEntity> proposals=new ArrayList<ProposalEntity>();
        ProfessionalEntity pro = professionalRepository.findById(id);
        proposalRepository.findBySender(pro.getUser()).forEach(element->proposals.add(element));
        return Proposal.convertProposalEntityListToProposalList(proposals);
    }
    @Override
    public List<Proposal> getProposalsByProjectId(String id) {
        List<ProposalEntity> proposals=new ArrayList<ProposalEntity>();
        ProjectEntity p = projectRepository.findById(id).orElseThrow();
        proposalRepository.findByProject(p).forEach(element->proposals.add(element));
        return Proposal.convertProposalEntityListToProposalList(proposals);
    }
    @Override
    public List<Proposal> getProposalsByReceiverId(String id) {
        List<ProposalEntity> proposals=new ArrayList<ProposalEntity>();
        User c = clientRepository.findById(id);
        proposalRepository.findByReceiver(c).forEach(element->proposals.add(element));
        return Proposal.convertProposalEntityListToProposalList(proposals);
    }
    @Override
    public Proposal getProposalsById(String id) {
        return Proposal.toModel(proposalRepository.findById(id).orElseThrow());
    }

    @Override
    public void deleteProposal(String id) {
        //checking if the Message exist in DB or not
        proposalRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("Proposal","id",id));
        proposalRepository.deleteById(id);
    }
}
