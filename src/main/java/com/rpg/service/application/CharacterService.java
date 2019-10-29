package com.rpg.service.application;

import com.rpg.model.application.Character;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import com.rpg.repository.application.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterService {

    @Autowired private CharacterRepository characterRepository;

    public List<Character> findByScenario(Scenario scenario){
        return characterRepository.findByScenario(scenario);
    }

    public List<Character> findByOwnerAndScenario(User owner, Scenario scenario){
        return characterRepository.findByOwnerAndScenario(owner, scenario);
    }

    public List<String> getCharacterNames(List<Character> characters){
        List<String> names = new ArrayList<>();
        characters.forEach(character -> names.add(character.getName()));
        return names;
    }

    public boolean isCharacterUsersProperty(String characterName, User user, Scenario scenario) {
        List<String> names = getCharacterNames(findByOwnerAndScenario(user, scenario));
        return names.contains(characterName);
    }

    public boolean existsWithName(String whisperTarget, Scenario scenario) {
        List<String> names = getCharacterNames(findByScenario(scenario));
        return names.contains(whisperTarget);
    }
}
