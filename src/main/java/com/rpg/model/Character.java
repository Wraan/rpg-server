package com.rpg.model;

public class Character {

    private String name;
    private String race;
    private String profession;

    public Character(String name, String race, String profession) {
        this.name = name;
        this.race = race;
        this.profession = profession;
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
}
