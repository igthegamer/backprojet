package work.example.demo.Repository;

import work.example.demo.entities.ProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProfessionalRepository extends JpaRepository<ProfessionalEntity, Long> {
    @Query("SELECT p FROM ProfessionalEntity p  WHERE p.firstName= ?1")
    Collection<? extends ProfessionalEntity> findByFirstName(List<String> firstName);
    @Query("SELECT p FROM ProfessionalEntity p  WHERE p.lastName= ?1")
    Collection<? extends ProfessionalEntity> findByLastName(List<String> lastName);
    @Query("SELECT p FROM ProfessionalEntity p  WHERE p.id= ?1")
    ProfessionalEntity findById(String Id);
    @Query("SELECT p FROM ProfessionalEntity p JOIN p.workPlaces wp WHERE wp IN ?1")
    List<ProfessionalEntity> searchByWorkPlaces(List<String> keywords);

}
