package com.rpg.controller;

import com.rpg.dto.application.CreateScenarioDto;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import com.rpg.service.application.ScenarioService;
import com.rpg.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
public class ScenarioController {

    @Autowired private ScenarioService scenarioService;
    @Autowired private UserService userService;

    @PostMapping("/scenario")
    public ResponseEntity<String> createSession(@RequestBody CreateScenarioDto scenarioDto, Principal principal){
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.createScenarioWithGameMaster(scenarioDto, user);
        return new ResponseEntity<>(scenario.getScenarioKey(), HttpStatus.OK);
    }

    //TODO get all scenarios where user is GM or player

}
