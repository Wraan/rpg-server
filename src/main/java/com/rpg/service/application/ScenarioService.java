package com.rpg.service.application;

import com.rpg.dto.application.*;
import com.rpg.exception.PrivilageException;
import com.rpg.exception.ScenarioDoesNotExistException;
import com.rpg.exception.ScenarioException;
import com.rpg.exception.UserAlreadyExistsException;
import com.rpg.model.application.*;
import com.rpg.model.application.Character;
import com.rpg.model.security.User;
import com.rpg.repository.application.NoteRepository;
import com.rpg.repository.application.ScenarioRepository;
import com.rpg.service.converter.ApplicationConverter;
import com.rpg.service.dnd.AbilitiesService;
import com.rpg.service.dnd.EquipmentService;
import com.rpg.service.dnd.TypesService;
import org.bouncycastle.openssl.PasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ScenarioService {

    @Autowired private ScenarioRepository scenarioRepository;
    @Autowired private NoteRepository noteRepository;
    @Autowired private CharacterService characterService;
    @Autowired private MessageService messageService;
    @Autowired private ScenarioSessionService scenarioSessionService;

    @Autowired private TypesService typesService;
    @Autowired private AbilitiesService abilitiesService;
    @Autowired private EquipmentService equipmentService;

    @Autowired private PasswordEncoder passwordEncoder;

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
                user, Collections.emptyList(), scenarioDto.getName());
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

    public List<Scenario> findUserScenarios(User user) {
        List<Scenario> list = scenarioRepository.findByGameMaster(user);
        list.addAll(user.getScenarios());
        return list;
    }

    public void joinScenario(User user, Scenario scenario, String password) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!passwordEncoder.matches(password, scenario.getPassword())) throw new PasswordException("Wrong password");
        if(isUserGameMasterInScenario(user, scenario))
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

    public boolean isUserPlayerOrGameMasterInScenario(User user, Scenario scenario){
        return isUserPlayerInScenario(user, scenario) || isUserGameMasterInScenario(user, scenario);
    }

    public void changePassword(SimplePasswordDto simplePasswordDto, Scenario scenario) throws Exception{
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");

        scenario.setPassword(passwordEncoder.encode(simplePasswordDto.getPassword()));
        save(scenario);
    }

    public void removePlayer(User player, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(isUserGameMasterInScenario(player, scenario))
            throw new ScenarioException("GameMaster cannot be removed from scenario");
        if(!isUserPlayerInScenario(player, scenario))
            throw new ScenarioException("User is not a player in that scenario");

        List<Character> characters = characterService.findByOwnerAndScenario(player, scenario);
        characters.forEach(it -> characterService.changeOwner(it, null));

        scenario.getPlayers().remove(player);
        save(scenario);
    }

    public ScenarioInfoResponse getScenarioInfo(Scenario scenario, User user) throws Exception {
        PlayersResponse playersResponse = getPlayersInScenario(user, scenario);
        return new ScenarioInfoResponse(scenario.getGameMaster().getUsername(),
                scenario.getScenarioKey(), playersResponse.getPlayers(), playersResponse.getOnlinePlayers(),
                scenarioSessionService.getScenarioSession(scenario).getScenarioStatusType().toString());
    }

    public void changeGameMaster(User user, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(isUserGameMasterInScenario(user, scenario))
            throw new ScenarioException("User is already a GameMaster in that scenario");
        if(!isUserPlayerInScenario(user, scenario))
            throw new ScenarioException("User is not a player in that scenario");

        User gm = scenario.getGameMaster();
        scenario.setGameMaster(user);
        scenario.getPlayers().remove(user);
        scenario.getPlayers().add(gm);
        save(scenario);
    }

    public PlayersResponse getPlayersInScenario(User user, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!isUserPlayerOrGameMasterInScenario(user, scenario))
            throw new ScenarioException("User is not a player in that scenario");

        Set<String> players = getPlayerNames(scenario.getPlayers());
        Set<String> onlinePlayers = getPlayerNames(scenarioSessionService.getOnlineUsersFromScenario(scenario));

        return new PlayersResponse(scenario.getGameMaster().getUsername(), players, onlinePlayers);
    }

    public boolean existsByScenarioKey(String scenarioKey){
        return scenarioRepository.existsByScenarioKey(scenarioKey);
    }

    public void startScenario(User gm, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!isUserGameMasterInScenario(gm, scenario))
            throw new PrivilageException("Only GameMaster can start the scenario");
        ScenarioSession scenarioSession = scenarioSessionService.getScenarioSession(scenario);
        if(scenarioSession.getScenarioStatusType().equals(ScenarioStatusType.STARTED))
            throw new ScenarioException("Scenario has been already started");
        if(!getPlayerNames(scenarioSessionService.getOnlineUsersFromScenario(scenario)).contains(gm.getUsername()))
            throw new ScenarioException("GameMaster has to be online");
        scenarioSessionService.changeScenarioSessionStatus(scenarioSession, ScenarioStatusType.STARTED);
    }

    private Set<String> getPlayerNames(Set<User> users){
        Set<String> playerNames = new HashSet<>();
        for(User player : users)
            playerNames.add(player.getUsername());
        return playerNames;
    }
    private Set<String> getPlayerNames(List<User> users){
        Set<String> playerNames = new HashSet<>();
        for(User player : users)
            playerNames.add(player.getUsername());
        return playerNames;
    }

    public List<Note> findNotesByUserAndScenario(User user, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!isUserPlayerOrGameMasterInScenario(user, scenario))
            throw new ScenarioException("User is not a player in that scenario");

        return noteRepository.findByUserAndScenario(user, scenario);
    }

    public Note findNoteByIdAndUserAndScenario(long noteId, User user, Scenario scenario) {
        return noteRepository.findByIdAndUserAndScenario(noteId, user, scenario);
    }

    public void deleteNote(Note note) {
        noteRepository.delete(note);
    }

    public void patchNote(Note note, NoteDto noteDto) {
        note.setName(noteDto.getName());
        note.setContent(noteDto.getContent());
        noteRepository.save(note);
    }

    public void createNote(NoteDto noteDto, User user, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!isUserPlayerOrGameMasterInScenario(user, scenario))
            throw new ScenarioException("User is not a player in that scenario");

        Note note = new Note(noteDto.getName(), noteDto.getContent(), user, scenario);
        noteRepository.save(note);
    }

    @Transactional
    public void deleteScenario(Scenario scenario, User gm) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!isUserGameMasterInScenario(gm, scenario))
            throw new PrivilageException("Only GameMaster can delete the scenario");

        noteRepository.deleteByScenario(scenario);
        characterService.deleteByScenario(scenario);
        messageService.deleteByScenario(scenario);

        typesService.deleteByScenario(scenario);
        abilitiesService.deleteByScenario(scenario);
        equipmentService.deleteByScenario(scenario);

        scenarioRepository.delete(scenario);
    }
}
