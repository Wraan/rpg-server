package com.rpg.dto.application;

import java.util.List;

public class ScenarioInfoResponse {

    private String gameMaster;
    private String scenarioKey;
    private List<String> players;
    private List<String> onlinePlayers;
    private String scenarioStatus;

    public ScenarioInfoResponse() {
    }

    public ScenarioInfoResponse(String gameMaster, String scenarioKey, List<String> players, List<String> onlinePlayers, String scenarioStatus) {
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

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public List<String> getOnlinePlayers() {
        return onlinePlayers;
    }

    public void setOnlinePlayers(List<String> onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }

    public String getScenarioStatus() {
        return scenarioStatus;
    }

    public void setScenarioStatus(String scenarioStatus) {
        this.scenarioStatus = scenarioStatus;
    }
}
