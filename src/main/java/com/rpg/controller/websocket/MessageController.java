package com.rpg.controller.websocket;

import com.rpg.dto.websocket.ActionMessageResponse;
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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @PostMapping("/action/message/scenario/{scenarioKey}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity message(@PathVariable("scenarioKey") String scenarioKey, @RequestBody MessageDto messageDto,
                                  Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        Message message;
        try {
            message = messageService.createMessage(messageDto, scenario, user);
            ActionMessageResponse amr = new ActionMessageResponse("message", messageConverter.messageToResponse(message));

            if(message.getType().equals(MessageType.Whisper)){
                //TODO test it
                Set<User> receivers = new HashSet<>();
                receivers.add(scenario.getGameMaster());
                User whisperTargetPlayer = characterService.findByNameAndScenario(message.getWhisperTarget(), scenario)
                        .getOwner();
                if(whisperTargetPlayer != null)
                    receivers.add(whisperTargetPlayer);
                receivers.add(message.getUser());

                for (User it : receivers)
                    template.convertAndSend("/ws/scenario/" + scenarioKey + "/player/" + it.getUsername(),
                            objectMapper.writeValueAsString(amr));
            }
            else{
                template.convertAndSend("/ws/scenario/" + scenarioKey,
                        objectMapper.writeValueAsString(amr));
            }
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/v1/message/{scenarioKey}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity collectUserMessages(@PathVariable("scenarioKey") String scenarioKey,
                                                     Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            List<Message> messages = messageService.findCorrespondingToUserInScenario(user, scenario);
            return ResponseEntity.ok(objectMapper.writeValueAsString(messageConverter.messagesToResponse(messages)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
