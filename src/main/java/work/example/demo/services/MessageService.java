package work.example.demo.services;


import org.springframework.stereotype.Service;
import work.example.demo.Model.Message;
import work.example.demo.Model.Project;

import java.util.List;

@Service
public interface MessageService {

    Message addMessage(Message message);

    List<Message> getMessagesBySenderId(String id);

    List<Message> getMessagesByProjectId(String id);

    List<Message> getMessagesByProject(Project project);

    void deleteMessage(String id);
}
