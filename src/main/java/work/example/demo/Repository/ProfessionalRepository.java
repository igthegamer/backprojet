package work.example.demo.Repository;

import work.example.demo.entities.ProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalRepository extends JpaRepository<ProfessionalEntity, Long> {
    @Query("SELECT p FROM ProfessionalEntity p  WHERE p.firstName= ?1")
    ProfessionalEntity findByFirstName(String firstName);
    @Query("SELECT p FROM ProfessionalEntity p  WHERE p.id= ?1")
    ProfessionalEntity findById(String Id);
}
