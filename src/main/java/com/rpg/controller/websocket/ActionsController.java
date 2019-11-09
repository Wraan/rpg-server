package com.rpg.controller.websocket;

import com.rpg.dto.websocket.ActionResponse;
import com.rpg.dto.websocket.DiceRollDto;
import com.rpg.model.application.Message;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import com.rpg.service.application.ActionService;
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
public class ActionsController {

    @Autowired private UserService userService;
    @Autowired private ScenarioService scenarioService;
    @Autowired private MessageService messageService;
    @Autowired private ActionService actionService;

    @Lazy @Autowired private SimpMessagingTemplate template;
    @Autowired private MessageConverter messageConverter;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Logger LOGGER = LogManager.getLogger(getClass());

    @GetMapping("/action/roll/scenario/{scenarioKey}")
    public ResponseEntity rollDices(@PathVariable("scenarioKey") String scenarioKey,
                                    @RequestBody DiceRollDto diceRollDto,
                                    Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            List<Integer> rolls = actionService.rollDicesInScenario(diceRollDto.getDices(), diceRollDto.getValue(),
                    user, scenario);
            //TODO check if character exists and is a property of a player
            Message message = messageService.createSystemMessage(diceRollDto.getCharacterName() + "rolled "
                    + diceRollDto.getDices() + "d" + diceRollDto.getValue() + " for " + rolls.toString(), scenario);
            LOGGER.info(message.getContent());

            ActionResponse amr = new ActionResponse("roll", messageConverter.messageToResponse(message));
            template.convertAndSend("/ws/scenario/" + scenarioKey,
                    objectMapper.writeValueAsString(amr));
            return ResponseEntity.ok("OK");
        } catch (Exception e){
            LOGGER.error(e.getStackTrace());
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }


}
