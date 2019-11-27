package com.rpg.service.dnd.character;

import com.rpg.dto.application.ChangeCharacterOwnerDto;
import com.rpg.dto.dnd.character.*;
import com.rpg.exception.*;
import com.rpg.model.dnd.character.*;
import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.character.Character;
import com.rpg.model.dnd.types.DamageType;
import com.rpg.model.security.User;
import com.rpg.repository.dnd.character.CharacterAbilitiesRepository;
import com.rpg.repository.dnd.character.CharacterEquipmentRepository;
import com.rpg.repository.dnd.character.CharacterRepository;
import com.rpg.repository.dnd.character.CharacterSpellsRepository;
import com.rpg.service.application.ScenarioService;
import com.rpg.service.dnd.AbilitiesService;
import com.rpg.service.dnd.EquipmentService;
import com.rpg.service.security.UserService;
import com.sun.media.sound.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private CharacterSpellsRepository characterSpellsRepository;
    @Autowired
    private CharacterAbilitiesRepository characterAbilitiesRepository;
    @Autowired
    private CharacterEquipmentRepository characterEquipmentRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private ScenarioService scenarioService;
    @Autowired
    private AbilitiesService abilitiesService;
    @Autowired
    private EquipmentService equipmentService;

    private final static String NAME_REGEX = "^[a-zA-Z0-9 ]{2,24}$";

    public List<Character> findByScenario(Scenario scenario) {
        return characterRepository.findByScenario(scenario);
    }

    public List<Character> findByOwnerAndScenario(User owner, Scenario scenario) {
        return characterRepository.findByOwnerAndScenario(owner, scenario);
    }

    public List<String> getCharacterNames(List<Character> characters) {
        List<String> names = new ArrayList<>();
        characters.forEach(character -> names.add(character.getName()));
        return names;
    }

    public boolean isCharacterUsersProperty(String characterName, User user, Scenario scenario) {
        if (scenarioService.isUserGameMasterInScenario(user, scenario)) {
            List<String> names = getCharacterNames(findByOwnerAndScenario(null, scenario));
            return names.contains(characterName);
        } else {
            List<String> names = getCharacterNames(findByOwnerAndScenario(user, scenario));
            return names.contains(characterName);
        }
    }

    public boolean existsWithName(String whisperTarget, Scenario scenario) {
        List<String> names = getCharacterNames(findByScenario(scenario));
        return names.contains(whisperTarget);
    }

    public Character createCharacter(CharacterDto dto, User user, Scenario scenario) throws Exception {
        if (scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if (!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
            throw new UserDoesNotExistException("User is not a player in that scenario");
        if (findByNameAndScenario(dto.getName(), scenario) != null)
            throw new CharacterException("Character with that name already exists");
        Pattern nameReg = Pattern.compile(NAME_REGEX);
        if (!nameReg.matcher(dto.getName()).matches())
            throw new RegexException("Character name must be simple - only letters, numbers and spaces 2-24 characters");
        if(Objects.isNull(dto.getAttributes()) || Objects.isNull(dto.getHealth()) || Objects.isNull(dto.getHitDices()))
            throw new InvalidDataException("Missing required value: attributes, health or hit dices");

        User owner = scenarioService.isUserGameMasterInScenario(user, scenario) ? null : user;

        Character character = new Character(dto.getName(), dto.getRace(), dto.getProfession(), dto.getLevel(),
                dto.getBackground(), dto.getExperience(), dto.getAlignment(), dto.getProficiency(), dto.getPassivePerception(),
                dto.getPassiveInsight(), dto.getInitiative(), dto.getSpeed(), dto.getInspiration(), new Attributes(dto.getAttributes().getStrength(),
                dto.getAttributes().getDexterity(), dto.getAttributes().getConstitution(), dto.getAttributes().getIntelligence(),
                dto.getAttributes().getWisdom(), dto.getAttributes().getCharisma()), new Health(dto.getHealth().getMaxHealth(),
                dto.getHealth().getTemporaryHealth(), dto.getHealth().getActualHealth()), new HitDices(dto.getHitDices().getDice(),
                dto.getHitDices().getTotal(), dto.getHitDices().getUsed()), owner, scenario);
        character.setAbilities(new CharacterAbilities());
        character.setSpells(new CharacterSpells());
        character.setEquipment(new CharacterEquipment());

        return characterRepository.save(character);
    }

    public Character findByNameAndScenario(String name, Scenario scenario) {
        return characterRepository.findByNameAndScenario(name, scenario);
    }

    public void delete(Character character, User user, Scenario scenario) throws Exception {
        if (scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if (character == null) throw new CharacterException("Character does not exist");
        if (!scenarioService.isUserGameMasterInScenario(user, scenario) &&
                (character.getOwner() == null || !character.getOwner().getUsername().equals(user.getUsername())))
            throw new CharacterException("Character does not belong to the player");

        characterRepository.delete(character);
    }

    public void changeCharactersOwnerInScenario(ChangeCharacterOwnerDto changeOwnerDto, Scenario scenario) throws Exception {
        if (scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");

        Character character = findByNameAndScenario(changeOwnerDto.getCharacterName(), scenario);
        if (character == null) throw new CharacterException("Character does not exist");

        User newOwner = userService.findByUsername(changeOwnerDto.getNewOwner());
        if (newOwner == null) throw new UserDoesNotExistException("New owner user does not exist");

        if (scenarioService.isUserGameMasterInScenario(newOwner, scenario))
            character.setOwner(null);
        else if (scenarioService.isUserPlayerInScenario(newOwner, scenario))
            character.setOwner(newOwner);
        else
            throw new UserDoesNotExistException("User is not a player or GameMaster in that scenario");

        characterRepository.save(character);
    }

    public void changeOwner(Character character, User player) {
        character.setOwner(player);
        characterRepository.save(character);
    }

    public void deleteByScenario(Scenario scenario) {
        characterRepository.deleteByScenario(scenario);
    }

    public void updateCharacterEquipment(Character character, CharacterEquipmentDto dto) throws Exception {
        CharacterEquipment characterEquipment = character.getEquipment();
        characterEquipment.setArmorClass(dto.getArmorClass());
        characterEquipment.setArmors(equipmentService.getArmorsFromNames(dto.getArmors(), character.getScenario()));
        characterEquipment.setGear(equipmentService.getGearFromNames(dto.getGear(), character.getScenario()));
        characterEquipment.setWeapons(equipmentService.getWeaponsFromNames(dto.getWeapons(), character.getScenario()));
        characterEquipment.setVehicles(equipmentService.getVehiclesFromNames(dto.getVehicles(), character.getScenario()));
        characterEquipment.setTools(equipmentService.getToolsFromNames(dto.getTools(), character.getScenario()));
        characterEquipment.getAttacks().clear();
        characterEquipment.getAttacks().addAll(equipmentService.createAttacks(dto.getAttacks(), characterEquipment, character.getScenario()));
        characterEquipment.setCurrency(equipmentService.createCurrency(dto.getCurrency()));

        characterEquipmentRepository.save(characterEquipment);
    }

    public void updateCharacterAbilities(Character character, CharacterAbilitiesDto dto) throws Exception {
        CharacterAbilities characterAbilities = character.getAbilities();
        characterAbilities.setFeatures(abilitiesService.getFeaturesFromNames(dto.getFeatures(), character.getScenario()));
        characterAbilities.setTraits(abilitiesService.getTraitsFromNames(dto.getTraits(), character.getScenario()));
        characterAbilities.setLanguages(abilitiesService.getLanguagesFromNames(dto.getLanguages(), character.getScenario()));
        characterAbilities.setProficiencies(abilitiesService.getProficienciesFromNames(dto.getProficiencies(), character.getScenario()));

        characterAbilitiesRepository.save(characterAbilities);
    }

    public void updateCharacterSpells(Character character, CharacterSpellsDto dto) throws Exception {
        CharacterSpells characterSpells = character.getSpells();
        characterSpells.setSpells(abilitiesService.getSpellsFromNames(dto.getSpells(), character.getScenario()));
        characterSpells.getSpellSlots().clear();
        characterSpells.getSpellSlots().addAll(abilitiesService.createSpellSlots(dto.getSpellSlots(), characterSpells));
        System.out.println(characterSpells.getSpellSlots().size());
        characterSpells.setSpellSaveDc(dto.getSpellSaveDc());
        characterSpells.setBaseStat(dto.getBaseStat());
        characterSpells.setSpellAttackBonus(dto.getSpellAttackBonus());

        characterSpellsRepository.save(characterSpells);
    }

    public void updateCharacter(Character character, CharacterDto dto) throws Exception {
        character.setRace(dto.getRace());
        character.setProfession(dto.getProfession());
        character.setLevel(dto.getLevel());
        character.setBackground(dto.getBackground());
        character.setExperience(dto.getExperience());
        character.setAlignment(dto.getAlignment());
        character.setProficiency(dto.getProficiency());
        character.setPassivePerception(dto.getPassivePerception());
        character.setPassiveInsight(dto.getPassiveInsight());
        character.setInitiative(dto.getInitiative());
        character.setSpeed(dto.getSpeed());
        character.setInspiration(dto.getInspiration());
        updateAttributes(character.getAttributes(), dto.getAttributes());
        updateHealth(character.getHealth(), dto.getHealth());
        updateHitDices(character.getHitDices(), dto.getHitDices());

        characterRepository.save(character);
    }

    private void updateAttributes(Attributes attributes, AttributesDto dto){
        attributes.setStrength(dto.getStrength());
        attributes.setDexterity(dto.getDexterity());
        attributes.setConstitution(dto.getConstitution());
        attributes.setIntelligence(dto.getIntelligence());
        attributes.setWisdom(dto.getWisdom());
        attributes.setCharisma(dto.getCharisma());
    }

    private void updateHealth(Health health, HealthDto dto) {
        health.setActualHealth(dto.getActualHealth());
        health.setMaxHealth(dto.getMaxHealth());
        health.setTemporaryHealth(dto.getActualHealth());
    }

    private void updateHitDices(HitDices hitDices, HitDicesDto dto) {
        hitDices.setDice(dto.getDice());
        hitDices.setTotal(dto.getTotal());
        hitDices.setUsed(dto.getUsed());
    }

}



