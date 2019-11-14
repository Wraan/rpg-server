package com.rpg.controller.application;

import com.rpg.dto.application.*;
import com.rpg.dto.websocket.MessageResponse;
import com.rpg.model.application.*;
import com.rpg.model.application.Character;
import com.rpg.model.security.User;
import com.rpg.service.application.CharacterService;
import com.rpg.service.application.MessageService;
import com.rpg.service.application.ScenarioService;
import com.rpg.service.application.ScenarioStatusService;
import com.rpg.service.converter.ApplicationConverter;
import com.rpg.service.converter.MessageConverter;
import com.rpg.service.security.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ScenarioController {

    @Autowired private ScenarioService scenarioService;
    @Autowired private UserService userService;
    @Autowired private CharacterService characterService;
    @Autowired private MessageService messageService;
    @Autowired private ScenarioStatusService scenarioStatusService;

    @Autowired private ApplicationConverter applicationConverter;
    @Autowired private MessageConverter messageConverter;
    private ObjectMapper objectMapper = new ObjectMapper();

    private Logger LOGGER = LogManager.getLogger(getClass());

    @PostMapping("/scenario")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity createScenario(@RequestBody CreateScenarioDto scenarioDto, Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.createScenarioWithGameMaster(scenarioDto, user);
        return ResponseEntity.ok().body(scenario.getScenarioKey());
    }

    @GetMapping("/scenario")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public List<ScenarioResponse> getUserScenarios(Principal principal){
        User user = userService.findByUsername(principal.getName());
        return scenarioService.findUserScenarios(user);
    }

    //TODO delete scenario and test if characters, messages and items are deleted properly

    @PostMapping("/scenario/{scenarioKey}/createCharacter")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity createCharacterInScenario(@PathVariable("scenarioKey") String scenarioKey,
                                                            @RequestBody CreateCharacterDto characterDto,
                                                            Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            characterService.createCharacter(characterDto, user, scenario);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/scenario/{scenarioKey}/character")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getCharactersInScenario(@PathVariable("scenarioKey") String scenarioKey,
                                                          Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            List<Character> characters = scenarioService.isUserGameMasterInScenario(user, scenario) ?
                    characterService.findByScenario(scenario) : characterService.findByOwnerAndScenario(user, scenario);
            return ResponseEntity.ok().body(applicationConverter.charactersToResponse(characters));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getStackTrace());
        }
    }

    @GetMapping("/scenario/{scenarioKey}/enter")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity enterScenario(@PathVariable("scenarioKey") String scenarioKey,
                                        Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            ScenarioStatus scenarioStatus = scenarioStatusService.getScenarioStatus(scenario);
            if(scenarioStatus.getScenarioStatusType().equals(ScenarioStatusType.STOPPED))
                scenarioStatusService.changeScenarioStatus(scenarioStatus, ScenarioStatusType.STANDBY);

            ScenarioInfoResponse scenarioInfo = scenarioService.getScenarioInfo(scenario, user);
            List<Character> characters = scenarioService.isUserGameMasterInScenario(user, scenario) ?
                    characterService.findByScenario(scenario) : characterService.findByOwnerAndScenario(user, scenario);
            List<CharacterResponse> charactersResponse = applicationConverter.charactersToResponse(characters);
            List<MessageResponse> messages = messageConverter.messagesToResponse(
                    messageService.findCorrespondingToUserInScenario(user, scenario));
            EnterScenarioResponse enterScenarioResponse = new EnterScenarioResponse(scenarioInfo, charactersResponse, messages);

            return ResponseEntity.ok().body(enterScenarioResponse);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/scenario/{scenarioKey}/players")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getPlayersInScenario(@PathVariable("scenarioKey") String scenarioKey,
                                        Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            PlayersResponse playersResponse = scenarioService.getPlayersInScenario(user, scenario);
            return ResponseEntity.ok().body(playersResponse);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
