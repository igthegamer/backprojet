package work.example.demo.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import work.example.demo.entities.ProjectEntity;
import work.example.demo.entities.ProposalEntity;
import work.example.demo.entities.User;

import java.util.List;

@Repository
public interface ProposalRepository extends JpaRepository<ProposalEntity, String> {
    @Query("select p from ProposalEntity p  where p.sender= ?1 ")
    List<ProposalEntity> findBySender(User user);

    @Query("select p from ProposalEntity p  where p.project= ?1 ")
    List<ProposalEntity> findByProject(ProjectEntity project) ;

    @Query("select p from ProposalEntity p  where p.id= ?1 ")
    ProposalEntity findByProposalId(String id);

    @Query("select p from ProposalEntity p  where p.receiver= ?1 ")
    List<ProposalEntity> findByReceiver(User client) ;
}
