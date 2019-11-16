package com.rpg.dto.dnd.equipment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArmorResponse {
    private long id;
    private String name;
    private ArmorClassJson armorClass;
    private int strMinimum;
    private boolean stealthDisadvantage;
    private int weight;
    private String cost;
    private boolean visible;
    private String creatorName;
    private String scenarioKey;

    public ArmorResponse() {
    }

    public ArmorResponse(long id, String name, ArmorClassJson armorClass, int strMinimum, boolean stealthDisadvantage, int weight, String cost, boolean visible, String creatorName, String scenarioKey) {
        this.id = id;
        this.name = name;
        this.armorClass = armorClass;
        this.strMinimum = strMinimum;
        this.stealthDisadvantage = stealthDisadvantage;
        this.weight = weight;
        this.visible = visible;
        this.cost = cost;
        this.creatorName = creatorName;
        this.scenarioKey = scenarioKey;
    }

    public String getScenarioKey() {
        return scenarioKey;
    }

    public void setScenarioKey(String scenarioKey) {
        this.scenarioKey = scenarioKey;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}
