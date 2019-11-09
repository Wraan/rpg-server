package com.rpg.controller.application;

import com.rpg.dto.application.CreateCharacterDto;
import com.rpg.dto.application.CreateScenarioDto;
import com.rpg.dto.application.ScenarioResponse;
import com.rpg.model.application.Character;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import com.rpg.service.application.CharacterService;
import com.rpg.service.application.ScenarioService;
import com.rpg.service.converter.ApplicationConverter;
import com.rpg.service.security.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ScenarioController {

    @Autowired private ScenarioService scenarioService;
    @Autowired private UserService userService;
    @Autowired private CharacterService characterService;

    @Autowired private ApplicationConverter applicationConverter;
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

    @PostMapping("/scenario/{scenarioKey}/enter")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity enterScenario(@PathVariable("scenarioKey") String scenarioKey,
                                                @RequestParam(value = "password") String password,
                                                Principal principal){
        User user = userService.findByUsername(principal.getName());
        try{
            scenarioService.enterScenario(user, scenarioKey, password);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

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
        // TODO WHY IT IS NOT WORKING FOR ADMIN ???
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Character> characters = scenario.getGameMaster().getUsername().equals(user.getUsername()) ?
                characterService.findByScenario(scenario) : characterService.findByOwnerAndScenario(user, scenario);
        try {
            applicationConverter.charactersToResponse(characters);
            return ResponseEntity.ok().header("Content-Type", "application/json")
                    .body(objectMapper.writeValueAsString(applicationConverter.charactersToResponse(characters)));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getStackTrace());
        }
    }

    @DeleteMapping("/scenario/{scenarioKey}/character/{name}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity<String> deleteCharacterFromScenario(@PathVariable("scenarioKey") String scenarioKey,
                                                          @PathVariable("name") String name,
                                                          Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);

        try {
            characterService.delete(name, user, scenario);
            return ResponseEntity.ok().body("OK");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
