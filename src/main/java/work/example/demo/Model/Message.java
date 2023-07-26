package work.example.demo.Model;


import work.example.demo.entities.MessageEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import work.example.demo.entities.MessageEntity;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class Message {
    private  String id;
    private String senderId;
    private String content;
    private Project project;



    public static MessageEntity toEntity(Message message) {
        return MessageEntity.builder().id(message.getId()).senderId(message.getSenderId()).content(message.getContent())
                .project(message.getProject()!=null?Project.toEntity(message.getProject()):null)
                .build();
    }
    public static Message toModel(MessageEntity messageEntity) {
        if(messageEntity==null)
            return null;
        return Message.builder().id(messageEntity.getId()).senderId(messageEntity.getSenderId())
                .content(messageEntity.getContent())
                .project(messageEntity.getProject()!=null?Project.toModel(messageEntity.getProject()):null)
                .build();
    }

    public static List<MessageEntity> convertMessageListToMessageEntityList(List<Message> messages){
        List<MessageEntity> conv= new ArrayList<MessageEntity>();
        messages.forEach(e->{
            conv.add(Message.toEntity(e));
        });
        return conv;
    }
    public static List<Message> convertMessageEntityListToMessageList(List<MessageEntity> messages){
        List<Message> conv= new ArrayList<Message>();
        messages.forEach(e->{
            conv.add(Message.toModel(e));
        });
        return conv;
    }
}
