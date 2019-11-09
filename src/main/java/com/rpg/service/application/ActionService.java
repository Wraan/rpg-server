package com.rpg.service.application;

import com.rpg.exception.ScenarioDoesNotExistException;
import com.rpg.exception.UserDoesNotExistException;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ActionService {

    @Autowired private ScenarioService scenarioService;

    public List<Integer> rollDicesInScenario(int dices, int value, User user, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserGameMasterInScenario(user, scenario) && !scenarioService.isUserPlayerInScenario(user, scenario))
            throw new UserDoesNotExistException("User is not a player in that scenario");

        if(dices <= 0 || value <= 0) throw new ValueException("Incorrect values. Values have to be greater than 0");
        Random random = new Random();
        List<Integer> rolls = new ArrayList<>();
        for(int i = 0; i < dices; i++) rolls.add(random.nextInt(value) + 1);
        return rolls;
    }
}
