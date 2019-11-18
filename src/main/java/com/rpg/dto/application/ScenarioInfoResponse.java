package com.rpg.dto.application;

import java.util.List;
import java.util.Set;

public class ScenarioInfoResponse {

    private String gameMaster;
    private String scenarioKey;
    private Set<String> players;
    private Set<String> onlinePlayers;
    private String scenarioStatus;

    public ScenarioInfoResponse() {
    }

    public ScenarioInfoResponse(String gameMaster, String scenarioKey, Set<String> players, Set<String> onlinePlayers, String scenarioStatus) {
        this.gameMaster = gameMaster;
        this.scenarioKey = scenarioKey;
        this.players = players;
        this.onlinePlayers = onlinePlayers;
        this.scenarioStatus = scenarioStatus;
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

    public Set<String> getPlayers() {
        return players;
    }

    public void setPlayers(Set<String> players) {
        this.players = players;
    }

    public Set<String> getOnlinePlayers() {
        return onlinePlayers;
    }

    public void setOnlinePlayers(Set<String> onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }

    public String getScenarioStatus() {
        return scenarioStatus;
    }

    public void setScenarioStatus(String scenarioStatus) {
        this.scenarioStatus = scenarioStatus;
    }
}
