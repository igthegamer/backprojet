package work.example.demo.services.imp;

import org.springframework.stereotype.Service;
import work.example.demo.Model.Message;
import work.example.demo.Model.Project;
import work.example.demo.Repository.MessageRepository;
import work.example.demo.Repository.ProjectRepository;
import work.example.demo.entities.MessageEntity;
import work.example.demo.entities.ProjectEntity;
import work.example.demo.handler.RessourceNotFoundException;
import work.example.demo.services.MessageService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ProjectRepository projectRepository;
    public MessageServiceImpl(MessageRepository messageRepository, ProjectRepository projectRepository) {
        this.messageRepository = messageRepository;
        this.projectRepository = projectRepository;
    }


    @Override
    public Message addMessage(Message message) {
        return Message.toModel(messageRepository.save(Message.toEntity(message)));
    }
    @Override
    public List<Message> getMessagesBySenderId(String id) {
        List<MessageEntity> messages=new ArrayList<MessageEntity>();
        messageRepository.findBySenderId(id).forEach(element->messages.add(element));
        return Message.convertMessageEntityListToMessageList(messages);
        }
    @Override
    public List<Message> getMessagesByProjectId(String id) {
        List<MessageEntity> messages=new ArrayList<MessageEntity>();
        ProjectEntity project= projectRepository.findById(id).orElseThrow();
        messageRepository.findByProject(project).forEach(element->messages.add(element));
        return Message.convertMessageEntityListToMessageList(messages);
    }
    @Override
    public List<Message> getMessagesByProject(Project project) {
        List<MessageEntity> messages=new ArrayList<MessageEntity>();
        messageRepository.findByProject(Project.toEntity(project)).forEach(element->messages.add(element));
        return Message.convertMessageEntityListToMessageList(messages);
    }

    @Override
    public void deleteMessage(String id) {
        messageRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("Message","id",id));
        messageRepository.deleteById(id);
    }
}


