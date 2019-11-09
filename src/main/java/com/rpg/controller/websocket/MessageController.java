package com.rpg.controller.websocket;

import com.rpg.dto.websocket.ActionResponse;
import com.rpg.dto.websocket.MessageDto;
import com.rpg.dto.websocket.MessageResponse;
import com.rpg.model.application.Message;
import com.rpg.model.application.MessageType;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import com.rpg.service.application.CharacterService;
import com.rpg.service.application.MessageService;
import com.rpg.service.application.ScenarioService;
import com.rpg.service.converter.MessageConverter;
import com.rpg.service.security.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class MessageController {

    @Autowired private UserService userService;
    @Autowired private ScenarioService scenarioService;
    @Autowired private MessageService messageService;
    @Autowired private CharacterService characterService;

    @Lazy @Autowired private SimpMessagingTemplate template;

    @Autowired private MessageConverter messageConverter;
    private ObjectMapper objectMapper = new ObjectMapper();
    private Logger LOGGER = LogManager.getLogger(getClass());

    @PostMapping("/message/scenario/{scenarioKey}")
    public ResponseEntity message(@PathVariable("scenarioKey") String scenarioKey, @RequestBody MessageDto messageDto,
                                  Principal principal){
        Message message;
        try {
            User user = userService.findByUsername(principal.getName());
            Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
            message = messageService.createMessage(messageDto, scenario, user);
            ActionResponse amr = new ActionResponse("message", messageConverter.messageToResponse(message));

            if(message.getType().equals(MessageType.Whisper)){
                User whisperTargetPlayer = characterService.findByNameAndScenario(message.getWhisperTarget(), scenario)
                        .getOwner();
                if(whisperTargetPlayer == null) whisperTargetPlayer = scenario.getGameMaster();
                template.convertAndSend("/ws/scenario/" + scenarioKey + "/player/" + whisperTargetPlayer.getUsername(),
                        objectMapper.writeValueAsString(amr));
                template.convertAndSend("/ws/scenario/" + scenarioKey + "/player/" + message.getUser().getUsername(),
                        objectMapper.writeValueAsString(amr));
            }
            else{
                template.convertAndSend("/ws/scenario/" + scenarioKey,
                        objectMapper.writeValueAsString(amr));
            }
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            LOGGER.error(e.getStackTrace());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/v1/message/{scenarioKey}")
    public List<MessageResponse> collectUserMessages(@PathVariable("scenarioKey") String scenarioKey,
                                                     Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Message> messages = messageService.findCorrespondingToUserInScenario(user, scenario);
        return messageConverter.messagesToResponse(messages);
    }



}
