package com.rpg.controller.websocket;

import com.rpg.dto.websocket.ActionMessageResponse;
import com.rpg.dto.websocket.MessageDto;
import com.rpg.dto.websocket.MessageResponse;
import com.rpg.model.application.Message;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import com.rpg.service.application.MessageService;
import com.rpg.service.application.ScenarioService;
import com.rpg.service.converter.MessageConverter;
import com.rpg.service.security.UserService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class MessageController {

    @Autowired private UserService userService;
    @Autowired private ScenarioService scenarioService;
    @Autowired private MessageService messageService;

    @Autowired private MessageConverter messageConverter;
    private ObjectMapper objectMapper = new ObjectMapper();


    @MessageMapping("/app/scenario/{scenarioKey}/message")
    @SendTo("/ws/scenario/{scenarioKey}/message")
    public String message(@DestinationVariable String scenarioKey, MessageDto messageDto){
        try {
            User user = userService.findWithToken(messageDto.getAccessToken());
            Message message = messageService.createMessage(messageDto, scenarioKey, user);
            MessageResponse messageResponse = messageConverter.messageToResponse(message);
            return objectMapper.writeValueAsString(new ActionMessageResponse("message", messageResponse));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping("/api/v1/message/{scenarioKey}")
    @ResponseBody
    public String messagetest(@PathVariable("scenarioKey") String scenarioKey, @RequestBody MessageDto messageDto){
        try {
            User user = userService.findWithToken(messageDto.getAccessToken());
            Message message = messageService.createMessage(messageDto, scenarioKey, user);
            MessageResponse messageResponse = messageConverter.messageToResponse(message);
            return objectMapper.writeValueAsString(new ActionMessageResponse("message", messageResponse));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/api/v1/message/{scenarioKey}")
    @ResponseBody
    public List<MessageResponse> collectUserMessages(@PathVariable("scenarioKey") String scenarioKey,
                                                     Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Message> messages = messageService.findCorrespondingToUserInScenario(user, scenario);
        return messageConverter.messagesToResponse(messages);
    }



}
