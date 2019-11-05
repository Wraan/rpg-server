package com.rpg.service.converter;

import com.rpg.dto.application.CharacterResponse;
import com.rpg.dto.application.ScenarioResponse;
import com.rpg.model.application.Character;
import com.rpg.model.application.Scenario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationConverter {

    public List<ScenarioResponse> scenariosToResponse(List<Scenario> list){
        List<ScenarioResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(scenarioToResponse(it));
        });
        return out;
    }

    public ScenarioResponse scenarioToResponse(Scenario it){
        return new ScenarioResponse(it.getName(), it.getGameMaster().getUsername(), it.getScenarioKey());
    }

    public List<CharacterResponse> charactersToResponse(List<Character> list){
        List<CharacterResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(characterToResponse(it));
        });
        return out;
    }

    public CharacterResponse characterToResponse(Character it){
        return new CharacterResponse(it.getName(), it.getRace(), it.getProfession(),
                it.getOwner() != null ? it.getOwner().getUsername() : null);
    }
}
