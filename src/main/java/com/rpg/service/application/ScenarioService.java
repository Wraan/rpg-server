package com.rpg.service.application;

import com.rpg.dto.application.CreateScenarioDto;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import com.rpg.repository.application.ScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ScenarioService {

    @Autowired private ScenarioRepository scenarioRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @Value("${scenario.key.length}")
    private int scenarioKeyLength;

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public Scenario findByKey(String key){
        return scenarioRepository.findByKey(key).orElse(null);
    }

    public Scenario save(Scenario scenario){
        return scenarioRepository.save(scenario);
    }

    public Scenario createScenarioWithGameMaster(CreateScenarioDto scenarioDto, User user) {
        String scenarioKey = generateScenarioKey();
        Scenario scenario = new Scenario(scenarioKey, passwordEncoder.encode(scenarioDto.getPassword()),
                user, Collections.emptyList(), scenarioDto.getName(), scenarioDto.getMaxPlayers());
        return scenarioRepository.save(scenario);
    }

    private String generateScenarioKey(){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < scenarioKeyLength; i++){
            int index = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            sb.append(ALPHA_NUMERIC_STRING.charAt(index));
        }
        return sb.toString();

    }
}
