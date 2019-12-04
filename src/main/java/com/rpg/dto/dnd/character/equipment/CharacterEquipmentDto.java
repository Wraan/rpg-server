package com.rpg.dto.dnd.character.equipment;

import com.rpg.dto.dnd.character.AttackDto;

import java.util.List;
import java.util.Set;

public class CharacterEquipmentDto {
    private String name;
    private int armorClass;
    private List<EquipmentAmountDto> armors;
    private List<EquipmentAmountDto> gear;
    private List<EquipmentAmountDto> vehicles;
    private List<EquipmentAmountDto> weapons;
    private List<EquipmentAmountDto> tools;
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

    public List<EquipmentAmountDto> getArmors() {
        return armors;
    }

    public void setArmors(List<EquipmentAmountDto> armors) {
        this.armors = armors;
    }

    public List<EquipmentAmountDto> getGear() {
        return gear;
    }

    public void setGear(List<EquipmentAmountDto> gear) {
        this.gear = gear;
    }

    public List<EquipmentAmountDto> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<EquipmentAmountDto> vehicles) {
        this.vehicles = vehicles;
    }

    public List<EquipmentAmountDto> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<EquipmentAmountDto> weapons) {
        this.weapons = weapons;
    }

    public List<EquipmentAmountDto> getTools() {
        return tools;
    }

    public void setTools(List<EquipmentAmountDto> tools) {
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
