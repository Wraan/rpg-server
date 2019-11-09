package com.rpg.dto.application;

public class ChangeCharacterOwnerDto {

    private String characterName;
    private String newOwner;

    public ChangeCharacterOwnerDto() {
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getNewOwner() {
        return newOwner;
    }

    public void setNewOwner(String newOwner) {
        this.newOwner = newOwner;
    }
}
