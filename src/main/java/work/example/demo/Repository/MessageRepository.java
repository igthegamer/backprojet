package work.example.demo.Repository;

import work.example.demo.entities.MessageEntity;
import work.example.demo.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity,String> {
    @Query("select m from MessageEntity m  where m.senderId= ?1 ")
    List<MessageEntity> findBySenderId(String id);

    @Query("select m from MessageEntity m  where m.project= ?1 ")
    List<MessageEntity> findByProject(ProjectEntity project) ;
}
