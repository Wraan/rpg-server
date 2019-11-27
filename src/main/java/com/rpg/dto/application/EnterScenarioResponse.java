package com.rpg.dto.application;

import com.rpg.dto.dnd.character.CharacterResponse;
import com.rpg.dto.websocket.MessageResponse;

import java.util.List;

public class EnterScenarioResponse {
    private ScenarioInfoResponse scenarioInfo;
    private List<CharacterResponse> userCharacters;
    private List<MessageResponse> messages;

    public EnterScenarioResponse() {
    }

    public EnterScenarioResponse(ScenarioInfoResponse scenarioInfo, List<CharacterResponse> userCharacters, List<MessageResponse> messages) {
        this.scenarioInfo = scenarioInfo;
        this.userCharacters = userCharacters;
        this.messages = messages;
    }

    public List<MessageResponse> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageResponse> messages) {
        this.messages = messages;
    }

    public ScenarioInfoResponse getScenarioInfo() {
        return scenarioInfo;
    }

    public void setScenarioInfo(ScenarioInfoResponse scenarioInfo) {
        this.scenarioInfo = scenarioInfo;
    }

    public List<CharacterResponse> getUserCharacters() {
        return userCharacters;
    }

    public void setUserCharacters(List<CharacterResponse> userCharacters) {
        this.userCharacters = userCharacters;
    }
}
