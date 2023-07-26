package work.example.demo.Repository;

import work.example.demo.entities.ProfessionalEntity;
import work.example.demo.entities.User;
import work.example.demo.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {
   @Query("select p from ProjectEntity p  where p.client= ?1 ")
   List<ProjectEntity> findByClient(User client);
    @Query("select p from ProjectEntity p  where p.id= ?1 ")
    Optional<ProjectEntity> findById(String id);
    @Query("select p from ProjectEntity p  where p.professional= ?1 ")
    List<ProjectEntity> findByProfessional(ProfessionalEntity professional);
}
