package com.rpg.dto.dnd.equipment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeaponResponse {
    private long id;
    private String name;
    private String category;
    private String weaponRange;
    private String damageDice;
    private int damageBonus;
    private String damageType;
    private int normalRange;
    private int longRange;
    private int normalThrowRange;
    private int longThrowRange;
    private List<String> properties;
    private int weight;
    private String cost;
    private boolean visible;
    private String creatorName;
    private String scenarioKey;

    public WeaponResponse() {
    }

    public WeaponResponse(long id, String name, String category, String weaponRange, String damageDice, int damageBonus, String damageType, int normalRange, int longRange, int normalThrowRange, int longThrowRange, List<String> properties, int weight, String cost, boolean visible, String creatorName, String scenarioKey) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.weaponRange = weaponRange;
        this.damageDice = damageDice;
        this.damageBonus = damageBonus;
        this.damageType = damageType;
        this.normalRange = normalRange;
        this.longRange = longRange;
        this.normalThrowRange = normalThrowRange;
        this.longThrowRange = longThrowRange;
        this.properties = properties;
        this.weight = weight;
        this.cost = cost;
        this.visible = visible;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWeaponRange() {
        return weaponRange;
    }

    public void setWeaponRange(String weaponRange) {
        this.weaponRange = weaponRange;
    }

    public String getDamageDice() {
        return damageDice;
    }

    public void setDamageDice(String damageDice) {
        this.damageDice = damageDice;
    }

    public int getDamageBonus() {
        return damageBonus;
    }

    public void setDamageBonus(int damageBonus) {
        this.damageBonus = damageBonus;
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public int getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(int normalRange) {
        this.normalRange = normalRange;
    }

    public int getLongRange() {
        return longRange;
    }

    public void setLongRange(int longRange) {
        this.longRange = longRange;
    }

    public int getNormalThrowRange() {
        return normalThrowRange;
    }

    public void setNormalThrowRange(int normalThrowRange) {
        this.normalThrowRange = normalThrowRange;
    }

    public int getLongThrowRange() {
        return longThrowRange;
    }

    public void setLongThrowRange(int longThrowRange) {
        this.longThrowRange = longThrowRange;
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
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
