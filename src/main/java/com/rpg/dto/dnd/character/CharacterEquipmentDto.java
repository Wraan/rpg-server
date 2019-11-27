package com.rpg.dto.dnd.character;

import java.util.List;
import java.util.Set;

public class CharacterEquipmentDto {
    private String name;
    private int armorClass;
    private Set<String> armors;
    private Set<String> gear;
    private Set<String> vehicles;
    private Set<String> weapons;
    private Set<String> tools;
    private List<AttackDto> attacks;
    private CurrencyDto currency;

    public CharacterEquipmentDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public Set<String> getArmors() {
        return armors;
    }

    public void setArmors(Set<String> armors) {
        this.armors = armors;
    }

    public Set<String> getGear() {
        return gear;
    }

    public void setGear(Set<String> gear) {
        this.gear = gear;
    }

    public Set<String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<String> vehicles) {
        this.vehicles = vehicles;
    }

    public Set<String> getWeapons() {
        return weapons;
    }

    public void setWeapons(Set<String> weapons) {
        this.weapons = weapons;
    }

    public Set<String> getTools() {
        return tools;
    }

    public void setTools(Set<String> tools) {
        this.tools = tools;
    }

    public List<AttackDto> getAttacks() {
        return attacks;
    }

    public void setAttacks(List<AttackDto> attacks) {
        this.attacks = attacks;
    }

    public CurrencyDto getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDto currency) {
        this.currency = currency;
    }
}
