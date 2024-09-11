package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message createMesssage(Message message) throws Exception{
        if(message.getMessageText() == null || message.getMessageText().isEmpty()){
            throw new IllegalArgumentException("Message can not be blank");
        }
        if(message.getMessageText().length() > 255){
            throw new IllegalArgumentException("Message can not be greater than 255 characters");
        }       

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int id){
        return messageRepository.findById(id).orElse(null);
    }

    public boolean deleteMessageById(int id){
        if(messageRepository.existsById(id)){
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean updateMessageById(int id, String newText){

        if(newText == null || newText.isEmpty() || newText.isBlank() || newText.length() > 255){
            return false;
        }

        if(messageRepository.existsById(id)){
            Message existingMessage = messageRepository.getById(id);
            existingMessage.setMessageText(newText);
            return true;
        }
       return false;
    }

    public List<Message> getAllMessagesByAccountId(int id){
        return messageRepository.findByPostedBy(id);
    }
}
