package com.rpg.dto.application;

public class CreateScenarioDto {

    private String name;
    private String password;

    public CreateScenarioDto() {
    }

    public CreateScenarioDto(String name, String password) {
        this.name = name;
        this.password = password;
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
}
