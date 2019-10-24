package com.rpg.dto.application;

public class ScenarioResponse {

    private String name;
    private String gameMaster;
    private String scenarioKey;

    public ScenarioResponse() {
    }

    public ScenarioResponse(String name, String gameMaster, String scenarioKey) {
        this.name = name;
        this.gameMaster = gameMaster;
        this.scenarioKey = scenarioKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGameMaster() {
        return gameMaster;
    }

    public void setGameMaster(String gameMaster) {
        this.gameMaster = gameMaster;
    }

    public String getScenarioKey() {
        return scenarioKey;
    }

    public void setScenarioKey(String scenarioKey) {
        this.scenarioKey = scenarioKey;
    }
}
