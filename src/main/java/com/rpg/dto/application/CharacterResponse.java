package com.rpg.dto.application;

public class CharacterResponse {
    private String name;
    private String race;
    private String profession;
    private String owner;

    public CharacterResponse() {
    }

    public CharacterResponse(String name, String race, String profession, String owner) {
        this.name = name;
        this.race = race;
        this.profession = profession;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
