package com.rpg.dto.application;

import java.util.List;

public class PlayersResponse {
    private String gameMaster;
    private List<String> players;
    private List<String> onlinePlayers;

    public PlayersResponse() {
    }

    public PlayersResponse(String gameMaster, List<String> players, List<String> onlinePlayers) {
        this.gameMaster = gameMaster;
        this.players = players;
        this.onlinePlayers = onlinePlayers;
    }

    public String getGameMaster() {
        return gameMaster;
    }

    public void setGameMaster(String gameMaster) {
        this.gameMaster = gameMaster;
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
}
