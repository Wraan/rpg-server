package com.rpg.dto.application;

public class CreateScenarioDto {

    private String name;
    private String password;
    private int maxPlayers;

    public CreateScenarioDto() {
    }

    public CreateScenarioDto(String name, String password, int maxPlayers) {
        this.name = name;
        this.password = password;
        this.maxPlayers = maxPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
}
