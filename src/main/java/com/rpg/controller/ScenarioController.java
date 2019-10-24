package com.rpg.controller;

import com.rpg.dto.application.CreateScenarioDto;
import com.rpg.dto.application.ScenarioResponse;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import com.rpg.service.application.ScenarioService;
import com.rpg.service.security.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @PostMapping("/scenario")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity<String> createSession(@RequestBody CreateScenarioDto scenarioDto, Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.createScenarioWithGameMaster(scenarioDto, user);
        return new ResponseEntity<>(scenario.getScenarioKey(), HttpStatus.OK);
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

    @PostMapping("/scenario/enter")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity<String> enterScenario(@RequestParam(value = "scenarioKey") String scenarioKey,
                                                @RequestParam(value = "password") String password,
                                                Principal principal){
        User user = userService.findByUsername(principal.getName());
        try{
            scenarioService.enterScenario(user, scenarioKey, password);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);


    }
}
