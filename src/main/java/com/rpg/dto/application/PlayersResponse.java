package com.rpg.dto.application;

import java.util.List;
import java.util.Set;

public class PlayersResponse {
    private String gameMaster;
    private Set<String> players;
    private Set<String> onlinePlayers;

    public PlayersResponse() {
    }

    public PlayersResponse(String gameMaster, Set<String> players, Set<String> onlinePlayers) {
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
}
