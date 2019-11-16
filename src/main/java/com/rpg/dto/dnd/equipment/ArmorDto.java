package com.rpg.dto.dnd.equipment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rpg.model.dnd.equipment.ArmorClass;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArmorDto {
    private String name;
    private ArmorClassJson armorClass;
    private int strMinimum;
    private boolean stealthDisadvantage;
    private int weight;
    private String cost;
    private boolean visible;

    public ArmorDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArmorClassJson getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(ArmorClassJson armorClass) {
        this.armorClass = armorClass;
    }

    public int getStrMinimum() {
        return strMinimum;
    }

    public void setStrMinimum(int strMinimum) {
        this.strMinimum = strMinimum;
    }

    public boolean isStealthDisadvantage() {
        return stealthDisadvantage;
    }

    public void setStealthDisadvantage(boolean stealthDisadvantage) {
        this.stealthDisadvantage = stealthDisadvantage;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
