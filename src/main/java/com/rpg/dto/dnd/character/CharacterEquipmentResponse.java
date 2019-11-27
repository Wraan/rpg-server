package com.rpg.dto.dnd.character;

import java.util.List;
import java.util.Set;

public class CharacterEquipmentResponse {

    private int armorClass;
    private Set<String> armors;
    private Set<String> gears;
    private Set<String> vehicles;
    private Set<String> tools;
    private Set<String> weapons;
    private List<AttackDto> attacks;
    private CurrencyDto currency;

    public CharacterEquipmentResponse() {
    }

    public CharacterEquipmentResponse(int armorClass, Set<String> armors, Set<String> gears, Set<String> vehicles,
                                      Set<String> tools, Set<String> weapons, List<AttackDto> attacks, CurrencyDto currency) {
        this.armorClass = armorClass;
        this.armors = armors;
        this.gears = gears;
        this.vehicles = vehicles;
        this.tools = tools;
        this.weapons = weapons;
        this.attacks = attacks;
        this.currency = currency;
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

    public Set<String> getGears() {
        return gears;
    }

    public void setGears(Set<String> gears) {
        this.gears = gears;
    }

    public Set<String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<String> vehicles) {
        this.vehicles = vehicles;
    }

    public Set<String> getTools() {
        return tools;
    }

    public void setTools(Set<String> tools) {
        this.tools = tools;
    }

    public Set<String> getWeapons() {
        return weapons;
    }

    public void setWeapons(Set<String> weapons) {
        this.weapons = weapons;
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
