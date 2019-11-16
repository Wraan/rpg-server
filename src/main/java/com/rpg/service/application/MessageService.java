package com.rpg.service.application;

import com.rpg.dto.websocket.MessageDto;
import com.rpg.exception.CharacterException;
import com.rpg.exception.ScenarioDoesNotExistException;
import com.rpg.exception.UserDoesNotExistException;
import com.rpg.model.application.Message;
import com.rpg.model.application.MessageType;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import com.rpg.repository.application.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired private ScenarioService scenarioService;
    @Autowired private MessageRepository messageRepository;
    @Autowired private CharacterService characterService;

    private int MESSAGES_AMOUNT_TO_RETURN = 50;
    private int PAGE_SIZE = 50;

    public Message createMessage(MessageDto messageDto, Scenario scenario, User user) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if (!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
            throw new UserDoesNotExistException("User is not a player in that scenario");
        if(!characterService.isCharacterUsersProperty(messageDto.getCharacterName(), user, scenario))
            throw new CharacterException("Character is not a property od a player");

        Message out;
        if(isOOC(messageDto.getContent().trim())){
            out = createOOCMessage(messageDto, user, scenario);
        } else if(isWhisper(messageDto.getContent())){
            out = createWhisperMessage(messageDto, user, scenario);
            if(!characterService.existsWithName(out.getWhisperTarget(), scenario))
                throw new CharacterException("Whisper target does not exist");
        } else{
            out = createCharacterMessage(messageDto, user, scenario);
        }
        return messageRepository.save(out);
    }

    private Message createOOCMessage(MessageDto messageDto, User user, Scenario scenario){
        String message = messageDto.getContent().substring(5);
        return new Message(message, user.getUsername(), MessageType.OOC, null, user, scenario);
    }

    private Message createWhisperMessage(MessageDto messageDto, User user, Scenario scenario){
        String message = messageDto.getContent().substring(3);
        String whisperTarget = message.split(" ")[0];
        message = message.replace(whisperTarget, "").trim();
        return new Message(message, messageDto.getCharacterName(), MessageType.Whisper, whisperTarget, user, scenario);
    }

    private Message createCharacterMessage(MessageDto messageDto, User user, Scenario scenario){
        String message = messageDto.getContent();
        return new Message(message, messageDto.getCharacterName(), MessageType.Character, null, user, scenario);
    }

    public Message createSystemMessage(String message, Scenario scenario){
        Message out = new Message(message, null, MessageType.System, null, null, scenario);
        return messageRepository.save(out);
    }

    private boolean isOOC(String message){
        return message.trim().startsWith("/ooc ");
    }

    private boolean isWhisper(String message){
        return message.trim().startsWith("/w ");
    }

    public List<Message> findCorrespondingToUserInScenario(User user, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
            throw new UserDoesNotExistException("User is not a player or GameMaster in that scenario");

        if(scenarioService.isUserGameMasterInScenario(user, scenario))
            return findNLastMessagesInScenario(scenario, 2*PAGE_SIZE);

        int page = 0;
        List<Message> messages = new ArrayList<>();
        List<Message> allMessages;
        List<String> playerCharacterNames = scenarioService.findUserCharacterNamesInScenario(user, scenario);
        do{
            allMessages = messageRepository.findByScenario(scenario, PageRequest.of(page, PAGE_SIZE));
            allMessages.forEach(it -> {
                if(messages.size() >= MESSAGES_AMOUNT_TO_RETURN) return;
                if(it.getType() == MessageType.OOC || it.getType() == MessageType.System
                    || it.getType() == MessageType.Character)
                    messages.add(it);
                else if(playerCharacterNames.contains(it.getWhisperTarget())) messages.add(it);
                else if(it.getUser().getUsername().equals(user.getUsername())) messages.add(it);
            });
            if(allMessages.size() < PAGE_SIZE) break;
            page++;
        } while (messages.size() < MESSAGES_AMOUNT_TO_RETURN);

        return  messages;
    }

    private List<Message> findNLastMessagesInScenario(Scenario scenario, int N) {
        return messageRepository.findByScenario(scenario, PageRequest.of(0, N));
    }
}
