package com.rpg.service.application;

import com.rpg.dto.application.CreateScenarioDto;
import com.rpg.dto.application.ScenarioResponse;
import com.rpg.exception.ScenarioDoesNotExistException;
import com.rpg.exception.UserAlreadyExistsException;
import com.rpg.model.application.Character;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import com.rpg.repository.application.ScenarioRepository;
import com.rpg.service.converter.ApplicationConverter;
import org.bouncycastle.openssl.PasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ScenarioService {

    @Autowired private ScenarioRepository scenarioRepository;
    @Autowired private CharacterService characterService;

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private ApplicationConverter applicationConverter;

    @Value("${scenario.key.length}")
    private int scenarioKeyLength;

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public Scenario findByScenarioKey(String key){
        return scenarioRepository.findByScenarioKey(key).orElse(null);
    }

    public Scenario save(Scenario scenario){
        return scenarioRepository.save(scenario);
    }

    public Scenario createScenarioWithGameMaster(CreateScenarioDto scenarioDto, User user) {
        String scenarioKey = generateScenarioKey();
        while (scenarioRepository.existsByScenarioKey(scenarioKey)) {
            scenarioKey = generateScenarioKey();
        }
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

    public List<ScenarioResponse> findUserScenarios(User user) {
        List<Scenario> list = scenarioRepository.findByGameMaster(user);
        list.addAll(user.getScenarios());
        return applicationConverter.scenariosToResponse(list);
    }

    public void enterScenario(User user, String scenarioKey, String password) throws Exception {
        Scenario scenario = findByScenarioKey(scenarioKey);
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!passwordEncoder.matches(password, scenario.getPassword())) throw new PasswordException("Wrong password");
        if(user.getUsername().equals(scenario.getGameMaster().getUsername()))
            throw new UserAlreadyExistsException("User is already a GameMaster of this Scenario");
        for(User player : scenario.getPlayers()){
            if(user.getUsername().equals(player.getUsername()))
                throw new UserAlreadyExistsException("User is already a player in that Scenario");
        }
        scenario.getPlayers().add(user);
        scenarioRepository.save(scenario);
    }

    public List<String> findUserCharacterNamesInScenario(User user, Scenario scenario) {
        List<Character> characters = characterService.findByOwnerAndScenario(user, scenario);
        return characterService.getCharacterNames(characters);
    }

    public boolean isUserPlayerInScenario(User user, Scenario scenario){
        return scenario.getPlayers().contains(user);
    }

    public boolean isUserGameMasterInScenario(User user, Scenario scenario){
        return scenario.getGameMaster().getUsername().equals(user.getUsername());
    }
}
