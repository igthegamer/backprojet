package work.example.demo.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import work.example.demo.entities.ProfessionalEntity;
import work.example.demo.entities.RealisationEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RealisationRepository extends JpaRepository<RealisationEntity, String> {
    @Query("select r from RealisationEntity r  where r.id= ?1 ")
    Optional<RealisationEntity> findById(String id);
    @Query("select r from RealisationEntity r  where r.professional= ?1 ")
    List<RealisationEntity> findByProfessional(ProfessionalEntity professional);
}

