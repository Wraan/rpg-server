package com.rpg.dto.application;

import java.util.List;

public class EnterScenarioResponse {
    private ScenarioInfoResponse scenarioInfo;
    private List<CharacterResponse> userCharacters;

    public EnterScenarioResponse() {
    }

    public EnterScenarioResponse(ScenarioInfoResponse scenarioInfo, List<CharacterResponse> userCharacters) {
        this.scenarioInfo = scenarioInfo;
        this.userCharacters = userCharacters;
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
