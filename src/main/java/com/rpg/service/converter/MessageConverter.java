package com.rpg.service.converter;

import com.rpg.dto.websocket.MessageResponse;
import com.rpg.model.application.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageConverter {

    public List<MessageResponse> messagesToResponse(List<Message> list){
        List<MessageResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(messageToResponse(it));
        });
        return out;
    }

    public MessageResponse messageToResponse(Message it){
        return new MessageResponse(it.getContent(), it.getSender(), it.getType().name().toLowerCase(), it.getWhisperTarget());
    }

}
