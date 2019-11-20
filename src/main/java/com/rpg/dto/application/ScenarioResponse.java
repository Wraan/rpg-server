package com.rpg.dto.application;

import java.util.List;
import java.util.Set;

public class ScenarioResponse {

    private String name;
    private String gameMaster;
    private String scenarioKey;
    private Set<String> onlinePlayers;

    public ScenarioResponse() {
    }

    public ScenarioResponse(String name, String gameMaster, String scenarioKey, Set<String> onlinePlayers) {
        this.name = name;
        this.gameMaster = gameMaster;
        this.scenarioKey = scenarioKey;
        this.onlinePlayers = onlinePlayers;
    }

    public Set<String> getOnlinePlayers() {
        return onlinePlayers;
    }

    public void setOnlinePlayers(Set<String> onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
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
