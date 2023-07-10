package work.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import work.example.demo.entities.User;

import java.util.Locale;
import java.util.Optional;
@Repository
@Transactional(readOnly = true)
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableUser(String email);
    @Query("SELECT c FROM User c  WHERE c.id= ?1")
    User findById(String Id);

}
