package com.rpg.controller.application;

import com.rpg.dto.application.*;
import com.rpg.dto.websocket.MessageResponse;
import com.rpg.exception.ScenarioDoesNotExistException;
import com.rpg.exception.ScenarioException;
import com.rpg.model.application.*;
import com.rpg.model.application.Character;
import com.rpg.model.security.User;
import com.rpg.service.application.*;
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
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class ScenarioController {

    @Autowired private ScenarioService scenarioService;
    @Autowired private UserService userService;
    @Autowired private CharacterService characterService;
    @Autowired private MessageService messageService;
    @Autowired private ScenarioSessionService scenarioSessionService;

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
        return applicationConverter.scenariosToResponse(scenarioService.findUserScenarios(user));
    }

    //TODO delete scenario and test if characters, messages and items are deleted properly

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

    @GetMapping("/scenario/{scenarioKey}/connect")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity connectToScenario(@PathVariable("scenarioKey") String scenarioKey,
                                        Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            ScenarioSession scenarioSession = scenarioSessionService.getScenarioSession(scenario);
            if(scenarioSession.getScenarioStatusType().equals(ScenarioStatusType.STOPPED))
                scenarioSessionService.changeScenarioSessionStatus(scenarioSession, ScenarioStatusType.STANDBY);

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

    @GetMapping("/scenario/{scenarioKey}/player")
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

    @GetMapping("/scenario/{scenarioKey}/message")
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
            return ResponseEntity.ok(messageConverter.messagesToResponse(messages));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/scenario/{scenarioKey}/start")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity startScenario(@PathVariable("scenarioKey") String scenarioKey,
                                              Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            scenarioService.startScenario(gm, scenario);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/scenario/{scenarioKey}/note")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getNotes(@PathVariable("scenarioKey") String scenarioKey,
                                   Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            return ResponseEntity.ok(applicationConverter.notesToResponse(
                    scenarioService.findNotesByUserAndScenario(user, scenario)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/scenario/{scenarioKey}/note")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity createNote(@PathVariable("scenarioKey") String scenarioKey,
                                     @RequestBody NoteDto noteDto,
                                     Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            scenarioService.createNote(noteDto, user, scenario);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/scenario/{scenarioKey}/note/{noteId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity patchNote(@PathVariable("scenarioKey") String scenarioKey,
                                    @PathVariable("noteId") long noteId,
                                    @RequestBody NoteDto noteDto,
                                    Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
                throw new ScenarioException("User is not a player in that scenario");

            Note note = scenarioService.findNoteByIdAndUserAndScenario(noteId, user, scenario);
            if(Objects.isNull(note))
                throw new ScenarioException("Note does not exist");

            scenarioService.patchNote(note, noteDto);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/scenario/{scenarioKey}/note/{noteId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity deleteNote(@PathVariable("scenarioKey") String scenarioKey,
                                    @PathVariable("noteId") long noteId,
                                    Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
                throw new ScenarioException("User is not a player in that scenario");

            Note note = scenarioService.findNoteByIdAndUserAndScenario(noteId, user, scenario);
            if(Objects.isNull(note))
                throw new ScenarioException("Note does not exist");

            scenarioService.deleteNote(note);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
