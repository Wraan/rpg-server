package com.rpg.service.application;

import com.rpg.model.application.Scenario;
import com.rpg.repository.application.ScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScenarioService {

    @Autowired
    private ScenarioRepository scenarioRepository;

    public Scenario findByKey(String key){
        return scenarioRepository.findByKey(key).orElse(null);
    }
}
