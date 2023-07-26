package work.example.demo.services.imp;


import work.example.demo.Repository.UserRepo;
import work.example.demo.entities.User;
import work.example.demo.services.SessionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {
    private final UserRepo userRepository;
    public SessionServiceImpl(UserRepo userRepository) {
        this.userRepository = userRepository;
    }
    public User getUserConnected() {
        if (SecurityContextHolder.getContext() == null)
            throw new RuntimeException("Not connected user !!");

        String id = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (id == null || "anonymousUser".equals(id))
            return null;

        // Use findById() to retrieve the User entity by ID
        return userRepository.findById(Long.valueOf(id)).orElse(null);
    }

}
