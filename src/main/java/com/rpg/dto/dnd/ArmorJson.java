package com.rpg.dto.dnd;

public class ArmorJson {

    private long id;
    private String name;
    private ArmorClassJson armorClass;
    private int strMinimum;
    private boolean stealthDisadvantage;
    private int weight;
    private String cost;

    public ArmorJson() {
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
}
