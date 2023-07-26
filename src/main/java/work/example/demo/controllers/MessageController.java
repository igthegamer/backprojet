package work.example.demo.controllers;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.example.demo.Model.Message;
import work.example.demo.Repository.MessageRepository;
import work.example.demo.entities.MessageEntity;
import work.example.demo.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("api/v1/messages")
public class MessageController {
    private final MessageService messageService;
private final MessageRepository messageRepository;
    public MessageController(MessageService messageService, MessageRepository messageRepository) {
        this.messageService = messageService;
        this.messageRepository = messageRepository;
    }

    @PostMapping
    public Message addMessage(@RequestBody Message message)
            throws Exception , JsonMappingException, JsonParseException {
        return messageService.addMessage(message);
    }
    @GetMapping("/{idProject}")
    public List<Message> getMessagesByProjectId(@PathVariable("idProject") String idProject){
        return this.messageService.getMessagesByProjectId(idProject);

    }
    @PutMapping()
    public Message updateMessage(@RequestBody Message message ) throws Exception {
        MessageEntity m = messageRepository.save(Message.toEntity(message));
        return Message.toModel(m);

    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable("id") String id){
        messageService.deleteMessage(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
