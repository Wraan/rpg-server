package com.rpg.service.application;

import com.rpg.dto.application.ChangeCharacterOwnerDto;
import com.rpg.dto.application.CreateCharacterDto;
import com.rpg.exception.CharacterException;
import com.rpg.exception.RegexException;
import com.rpg.exception.ScenarioDoesNotExistException;
import com.rpg.exception.UserDoesNotExistException;
import com.rpg.model.application.Character;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import com.rpg.repository.application.CharacterRepository;
import com.rpg.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class CharacterService {

    @Autowired private CharacterRepository characterRepository;
    @Autowired private UserService userService;
    @Autowired private ScenarioService scenarioService;

    private final static String NAME_REGEX = "^[A-Z][a-z]{1,24}$";

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
        if (scenario.getGameMaster().getUsername().equals(user.getUsername())){
            List<String> names = getCharacterNames(findByOwnerAndScenario(null, scenario));
            return names.contains(characterName);
        }
        else {
            List<String> names = getCharacterNames(findByOwnerAndScenario(user, scenario));
            return names.contains(characterName);
        }

    }

    public boolean existsWithName(String whisperTarget, Scenario scenario) {
        List<String> names = getCharacterNames(findByScenario(scenario));
        return names.contains(whisperTarget);
    }

    public void createCharacter(CreateCharacterDto characterDto, User user, Scenario scenario) throws Exception{
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenario.getPlayers().contains(user) && !scenario.getGameMaster().getUsername().equals(user.getUsername()))
            throw new UserDoesNotExistException("User is not a player in that scenario");
        if(findByNameAndScenario(characterDto.getName(), scenario) != null)
            throw new CharacterException("Character with that name already exists");
        Pattern nameReg = Pattern.compile(NAME_REGEX);
        if(!nameReg.matcher(characterDto.getName()).matches())
            throw new RegexException("Character name must be simple - only letters, starting with capital letter, up to 24 letters");

        User owner = user.getUsername().equals(scenario.getGameMaster().getUsername()) ? null : user;

        Character character = new Character(characterDto.getName(), characterDto.getRace(),
                characterDto.getProfession(), owner, scenario);
        characterRepository.save(character);
    }

    public Character findByNameAndScenario(String name, Scenario scenario){
        return characterRepository.findByNameAndScenario(name, scenario);
    }

    public void delete(String name, User user, Scenario scenario) throws Exception {
        Character character = findByNameAndScenario(name, scenario);
        if(character == null) throw new CharacterException("Character does not exist");
        if (character.getOwner().getUsername().equals(user.getUsername())
                || scenario.getGameMaster().getUsername().equals(user.getUsername())){
            characterRepository.delete(character);
        }
        throw new CharacterException("Character does not belong to the player");
    }

    public void changeCharactersOwnerInScenario(ChangeCharacterOwnerDto changeOwnerDto, Scenario scenario) throws Exception {
        Character character = findByNameAndScenario(changeOwnerDto.getCharacterName(), scenario);
        if(character == null) throw new CharacterException("Character does not exist");

        User newOwner = userService.findByUsername(changeOwnerDto.getNewOwner());
        if(newOwner == null) throw new UserDoesNotExistException("User does not exist");
        if(scenarioService.isUserGameMasterInScenario(newOwner, scenario))
            character.setOwner(null);
        else if(scenarioService.isUserPlayerInScenario(newOwner, scenario))
            character.setOwner(newOwner);
        else
            throw new UserDoesNotExistException("User is not a player or GameMaster in that scenario");

        characterRepository.save(character);
    }

    public void changeOwner(Character character, User player) {
        character.setOwner(player);
        characterRepository.save(character);

    }
}
