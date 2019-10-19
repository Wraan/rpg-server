package com.rpg.controller;

import com.rpg.model.application.Scenario;
import com.rpg.repository.application.ScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ScenarioController {

    @Autowired private ScenarioRepository scenarioRepository;

    @GetMapping("/testScenario")
    public Scenario generateTestScenario(){
        Scenario scenario = new Scenario("TESTSCEN");
        scenario.setId(1);
        return scenarioRepository.save(scenario);
    }

}
