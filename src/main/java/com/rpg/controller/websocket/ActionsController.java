package com.rpg.controller.websocket;

import com.rpg.dto.application.ChangeCharacterOwnerDto;
import com.rpg.dto.application.SimplePasswordDto;
import com.rpg.dto.websocket.ActionMessageResponse;
import com.rpg.dto.websocket.ActionUpdateResponse;
import com.rpg.dto.websocket.DiceRollDto;
import com.rpg.exception.PrivilageException;
import com.rpg.exception.UserDoesNotExistException;
import com.rpg.model.application.Message;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import com.rpg.service.application.ActionService;
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
@RequestMapping("/action")
public class ActionsController {

    @Autowired private UserService userService;
    @Autowired private ScenarioService scenarioService;
    @Autowired private MessageService messageService;
    @Autowired private ActionService actionService;
    @Autowired private CharacterService characterService;

    @Lazy @Autowired private SimpMessagingTemplate template;
    @Autowired private MessageConverter messageConverter;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Logger LOGGER = LogManager.getLogger(getClass());

    @PostMapping("/roll/scenario/{scenarioKey}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity rollDices(@PathVariable("scenarioKey") String scenarioKey,
                                    @RequestBody DiceRollDto diceRollDto,
                                    Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            if(!characterService.isCharacterUsersProperty(diceRollDto.getCharacterName(), user, scenario))
                throw new UserDoesNotExistException("Character is not a property of a player");

            List<Integer> rolls = actionService.rollDicesInScenario(diceRollDto.getDices(), diceRollDto.getValue(),
                    user, scenario);
            Message message = messageService.createSystemMessage(diceRollDto.getCharacterName() + " rolled "
                    + diceRollDto.getDices() + "d" + diceRollDto.getValue() + " for " + rolls.toString(), scenario);

            ActionMessageResponse amr = new ActionMessageResponse("message", messageConverter.messageToResponse(message));
            template.convertAndSend("/ws/scenario/" + scenarioKey,
                    objectMapper.writeValueAsString(amr));
            return ResponseEntity.ok("OK");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/change/characterOwner/scenario/{scenarioKey}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity changeCharacterOwner(@PathVariable("scenarioKey") String scenarioKey,
                                               @RequestBody ChangeCharacterOwnerDto changeOwnerDto,
                                               Principal principal) {
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if (!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can change characters owner");

            User oldOwner = characterService.findByNameAndScenario(changeOwnerDto.getCharacterName(), scenario).getOwner();
            characterService.changeCharactersOwnerInScenario(changeOwnerDto, scenario);

            Set<User> usersToUpdate = new HashSet<>();

            usersToUpdate.add(gm);
            if (oldOwner != null)
                usersToUpdate.add(oldOwner);
            User newOwner = characterService.findByNameAndScenario(changeOwnerDto.getCharacterName(), scenario).getOwner();
            if (newOwner != null)
                usersToUpdate.add(newOwner);

            for (User user: usersToUpdate)
                template.convertAndSend("/ws/scenario/" + scenarioKey + "/player/" + user.getUsername(),
                        objectMapper.writeValueAsString(new ActionUpdateResponse("reload", "character")));

            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/change/password/scenario/{scenarioKey}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })

    public ResponseEntity changeScenarioPassword(@PathVariable("scenarioKey") String scenarioKey,
                                                 @RequestBody SimplePasswordDto simplePasswordDto,
                                                 Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if (!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can change scenario password");

            scenarioService.changePassword(simplePasswordDto, scenario);

            return ResponseEntity.ok("OK");
        } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body(e.getMessage());
            }
    }


    @PostMapping("/join/scenario/{scenarioKey}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity joinScenario(@PathVariable("scenarioKey") String scenarioKey,
                                       @RequestBody SimplePasswordDto passwordDto,
                                       Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            scenarioService.joinScenario(user, scenario, passwordDto.getPassword());

            Message message = messageService.createSystemMessage(
                    "Player " + user.getUsername() + " has joined the scenario."
                    , scenario);
            ActionMessageResponse amr = new ActionMessageResponse("message", messageConverter.messageToResponse(message));
            template.convertAndSend("/ws/scenario/" + scenarioKey,
                    objectMapper.writeValueAsString(amr));

            template.convertAndSend("/ws/scenario/" + scenarioKey,
                    objectMapper.writeValueAsString(new ActionUpdateResponse("reload", "players")));

            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/remove/player/scenario/{scenarioKey}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity removePlayerFromScenario(@PathVariable("scenarioKey") String scenarioKey,
                                                   @RequestParam("player") String playerName,
                                                   Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if (!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can remove players from scenario");

            User player = userService.findByUsername(playerName);
            scenarioService.removePlayer(player, scenario);

            Message message = messageService.createSystemMessage(
                    "Player " + player.getUsername() + " has left the scenario."
                    , scenario);
            ActionMessageResponse amr = new ActionMessageResponse("message", messageConverter.messageToResponse(message));
            template.convertAndSend("/ws/scenario/" + scenarioKey,
                    objectMapper.writeValueAsString(amr));

            template.convertAndSend("/ws/scenario/" + scenarioKey + "/player/" + player.getUsername(),
                    objectMapper.writeValueAsString(new ActionUpdateResponse("leave", "scenario")));
            template.convertAndSend("/ws/scenario/" + scenarioKey,
                    objectMapper.writeValueAsString(new ActionUpdateResponse("reload", "players")));


            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/change/gameMaster/scenario/{scenarioKey}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity changeGameMaster(@PathVariable("scenarioKey") String scenarioKey,
                                           @RequestParam("player") String playerName,
                                           Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if (!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can change GameMaster in that scenario");

            User player = userService.findByUsername(playerName);
            scenarioService.changeGameMaster(player, scenario);

            Message message = messageService.createSystemMessage(
                    "GameMaster has been changed. Now " + player.getUsername() + " will guide your in your adventure."
                    , scenario);
            ActionMessageResponse amr = new ActionMessageResponse("message", messageConverter.messageToResponse(message));
            template.convertAndSend("/ws/scenario/" + scenarioKey,
                    objectMapper.writeValueAsString(amr));

            template.convertAndSend("/ws/scenario/" + scenarioKey + "/player/" + gm.getUsername(),
                    objectMapper.writeValueAsString(new ActionUpdateResponse("reload", "gameMaster")));
            template.convertAndSend("/ws/scenario/" + scenarioKey + "/player/" + player.getUsername(),
                    objectMapper.writeValueAsString(new ActionUpdateResponse("reload", "gameMaster")));

            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
