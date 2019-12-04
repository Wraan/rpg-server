package com.rpg.dto.dnd.character.equipment;

import com.rpg.dto.dnd.character.AttackDto;

import java.util.List;

public class CharacterEquipmentResponse {

    private int armorClass;
    private List<EquipmentAmountDto> armors;
    private List<EquipmentAmountDto> gears;
    private List<EquipmentAmountDto> vehicles;
    private List<EquipmentAmountDto> tools;
    private List<EquipmentAmountDto> weapons;
    private List<AttackDto> attacks;
    private CurrencyDto currency;

    public CharacterEquipmentResponse() {
    }

    public CharacterEquipmentResponse(int armorClass, List<EquipmentAmountDto> armors,
                                      List<EquipmentAmountDto> gears, List<EquipmentAmountDto> vehicles,
                                      List<EquipmentAmountDto> tools, List<EquipmentAmountDto> weapons,
                                      List<AttackDto> attacks, CurrencyDto currency) {
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

    public List<EquipmentAmountDto> getArmors() {
        return armors;
    }

    public void setArmors(List<EquipmentAmountDto> armors) {
        this.armors = armors;
    }

    public List<EquipmentAmountDto> getGears() {
        return gears;
    }

    public void setGears(List<EquipmentAmountDto> gears) {
        this.gears = gears;
    }

    public List<EquipmentAmountDto> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<EquipmentAmountDto> vehicles) {
        this.vehicles = vehicles;
    }

    public List<EquipmentAmountDto> getTools() {
        return tools;
    }

    public void setTools(List<EquipmentAmountDto> tools) {
        this.tools = tools;
    }

    public List<EquipmentAmountDto> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<EquipmentAmountDto> weapons) {
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
