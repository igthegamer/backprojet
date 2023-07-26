package work.example.demo.services;

import org.springframework.stereotype.Service;
import work.example.demo.Model.Proposal;

import java.util.List;

@Service
public interface ProposalService {
    Proposal addProposal(Proposal proposal);
    List<Proposal> getProposalsBySenderId(String id);

    List<Proposal> getProposalsByProjectId(String id);

    List<Proposal> getProposalsByReceiverId(String id);

    Proposal getProposalsById(String id);

    void deleteProposal(String id);
}
