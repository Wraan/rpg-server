package com.rpg.service.application;

import com.rpg.dto.application.ChangeCharacterOwnerDto;
import com.rpg.dto.application.character.CharacterAbilitiesDto;
import com.rpg.dto.application.character.CharacterEquipmentDto;
import com.rpg.dto.application.character.CharacterSpellsDto;
import com.rpg.dto.application.character.CharacterDto;
import com.rpg.exception.CharacterException;
import com.rpg.exception.RegexException;
import com.rpg.exception.ScenarioDoesNotExistException;
import com.rpg.exception.UserDoesNotExistException;
import com.rpg.model.application.character.*;
import com.rpg.model.application.Scenario;
import com.rpg.model.application.character.Character;
import com.rpg.model.security.User;
import com.rpg.repository.application.character.CharacterAbilitiesRepository;
import com.rpg.repository.application.character.CharacterEquipmentRepository;
import com.rpg.repository.application.character.CharacterRepository;
import com.rpg.repository.application.character.CharacterSpellsRepository;
import com.rpg.service.dnd.AbilitiesService;
import com.rpg.service.dnd.EquipmentService;
import com.rpg.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class CharacterService {

    @Autowired private CharacterRepository characterRepository;
    @Autowired private CharacterSpellsRepository characterSpellsRepository;
    @Autowired private CharacterAbilitiesRepository characterAbilitiesRepository;
    @Autowired private CharacterEquipmentRepository characterEquipmentRepository;

    @Autowired private UserService userService;
    @Autowired private ScenarioService scenarioService;
    @Autowired private AbilitiesService abilitiesService;
    @Autowired private EquipmentService equipmentService;

    private final static String NAME_REGEX = "^[a-zA-Z0-9 ]{2,24}$";

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
        if (scenarioService.isUserGameMasterInScenario(user, scenario)){
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

    public Character createCharacter(CharacterDto dto, User user, Scenario scenario) throws Exception{
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
            throw new UserDoesNotExistException("User is not a player in that scenario");
        if(findByNameAndScenario(dto.getName(), scenario) != null)
            throw new CharacterException("Character with that name already exists");
        Pattern nameReg = Pattern.compile(NAME_REGEX);
        if(!nameReg.matcher(dto.getName()).matches())
            throw new RegexException("Character name must be simple - only letters, numbers and spaces 2-24 characters");

        User owner = scenarioService.isUserGameMasterInScenario(user, scenario) ? null : user;

        Character character = new Character(dto.getName(), dto.getRace(), dto.getProfession(), dto.getLevel(),
                dto.getBackground(), dto.getExperience(), dto.getAlignment(), dto.getProficiency(), dto.getPassivePerception(),
                dto.getPassiveInsight(), dto.getInitiative(), dto.getSpeed(), dto.getInspiration(), new Attributes(dto.getAttributes().getStrength(),
                dto.getAttributes().getDexterity(), dto.getAttributes().getConstitution(), dto.getAttributes().getIntelligence(),
                dto.getAttributes().getWisdom(),  dto.getAttributes().getCharisma()), new Health(dto.getHealth().getMaxHealth(),
                dto.getHealth().getTemporaryHealth(), dto.getHealth().getActualHealth()), new HitDices(dto.getHitDices().getDice(),
                dto.getHitDices().getTotal(), dto.getHitDices().getUsed()), owner, scenario);
        character.setAbilities(new CharacterAbilities());
        character.setSpells(new CharacterSpells());
        character.setEquipment(new CharacterEquipment());

        return characterRepository.save(character);
    }

    public Character findByNameAndScenario(String name, Scenario scenario){
        return characterRepository.findByNameAndScenario(name, scenario);
    }

    public void delete(Character character, User user, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(character == null) throw new CharacterException("Character does not exist");
        if (!scenarioService.isUserGameMasterInScenario(user, scenario) &&
                (character.getOwner() == null || !character.getOwner().getUsername().equals(user.getUsername())))
            throw new CharacterException("Character does not belong to the player");

        characterRepository.delete(character);

    }

    public void changeCharactersOwnerInScenario(ChangeCharacterOwnerDto changeOwnerDto, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");

        Character character = findByNameAndScenario(changeOwnerDto.getCharacterName(), scenario);
        if(character == null) throw new CharacterException("Character does not exist");

        User newOwner = userService.findByUsername(changeOwnerDto.getNewOwner());
        if(newOwner == null) throw new UserDoesNotExistException("New owner user does not exist");

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

    public void deleteByScenario(Scenario scenario){
        characterRepository.deleteByScenario(scenario);
    }

    public void updateCharacterEquipment(Character character, CharacterEquipmentDto dto){

    }

    public void updateCharacterAbilities(Character character, CharacterAbilitiesDto dto) throws Exception{
        CharacterAbilities characterAbilities = character.getAbilities();
        characterAbilities.setFeatures(abilitiesService.getFeaturesFromNames(dto.getFeatures(), character.getScenario()));
        characterAbilities.setTraits(abilitiesService.getTraitsFromNames(dto.getTraits(), character.getScenario()));
        characterAbilities.setLanguages(abilitiesService.getLanguagesFromNames(dto.getLanguages(), character.getScenario()));
        characterAbilities.setProficiencies(abilitiesService.getProficienciesFromNames(dto.getProficiencies(), character.getScenario()));
        characterAbilitiesRepository.save(characterAbilities);

    }

    public void updateCharacterSpells(Character character, CharacterSpellsDto dto){

    }

    public void updateCharacter(Character character, CharacterDto dto) {
    }


