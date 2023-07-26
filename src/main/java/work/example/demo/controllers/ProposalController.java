package work.example.demo.controllers;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.example.demo.Model.Project;
import work.example.demo.Model.Proposal;
import work.example.demo.Repository.ProposalRepository;
import work.example.demo.entities.ProposalEntity;
import work.example.demo.services.ProjectService;
import work.example.demo.services.ProposalService;

import java.util.List;

@RestController
@RequestMapping("api/v1/proposals")
public class ProposalController {
    private final ProposalService proposalService;
    private final ProjectService projectService;
    private final ProposalRepository proposalRepository;

    public ProposalController(ProposalService proposalService, ProjectService projectService, ProposalRepository proposalRepository) {
        this.proposalService = proposalService;
        this.projectService = projectService;
        this.proposalRepository = proposalRepository;
    }

    @PostMapping
    public Proposal addProposal(@RequestBody Proposal proposal)
            throws Exception , JsonMappingException, JsonParseException {
        return proposalService.addProposal(proposal);
    }
    @GetMapping("/project/{id}")
    public List<Proposal> getProposalsByProject(@PathVariable("id") String id){
        return this.proposalService.getProposalsByProjectId(id);
    }
    @PutMapping()
    public Proposal updateProposal(@RequestBody Proposal proposal ) throws Exception {
        ProposalEntity p = proposalRepository.save(Proposal.toEntity(proposal));
        return Proposal.toModel(p);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProposal(@PathVariable("id") String id){
        proposalService.deleteProposal(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/receiver/{id}")
    public List<Proposal> getProposalsByReceiver(@PathVariable("id") String id){
        return this.proposalService.getProposalsByReceiverId(id);
    }
    @GetMapping("{id}")
    public Proposal getProposalsById(@PathVariable("id") String id){
        return this.proposalService.getProposalsById(id);
    }
    @GetMapping("/sender/{id}")
    public List<Proposal> getProposalsBySenderId(@PathVariable("id") String id){
        return this.proposalService.getProposalsBySenderId(id);
    }
    @PutMapping("/push_to_sender/{idSender}")
    public Project addProjectToPro(@PathVariable("idSender") String id, @RequestBody Project project) throws Exception {
        return projectService.addProjectToSender(id,project);
    }
}
