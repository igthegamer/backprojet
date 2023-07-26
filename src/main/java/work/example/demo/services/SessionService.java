package work.example.demo.services;

import work.example.demo.entities.User;

public interface SessionService {
    User getUserConnected() throws RuntimeException;
}
