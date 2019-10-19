package com.rpg.dto.dnd.equipment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rpg.dto.dnd.DamageTypeJson;
import com.rpg.dto.dnd.types.WeaponPropertyJson;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeaponJson {

    private long id;
    private String name;
    private String category;
    private String weaponRange;
    private String damageDice;
    private int damageBonus;
    private DamageTypeJson damageType;
    private int normalRange;
    private int longRange;
    private int normalThrowRange;
    private int longThrowRange;
    private List<WeaponPropertyJson> properties;
    private int weight;
    private String cost;
    private long creator;
    private String scenarioKey;

    public WeaponJson() {
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public String getScenarioKey() {
        return scenarioKey;
    }

    public void setScenarioKey(String scenarioKey) {
        this.scenarioKey = scenarioKey;
    }

    public String getWeaponRange() {
        return weaponRange;
    }

    public void setWeaponRange(String weaponRange) {
        this.weaponRange = weaponRange;
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

    public DamageTypeJson getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageTypeJson damageType) {
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

    public List<WeaponPropertyJson> getProperties() {
        return properties;
    }

    public void setProperties(List<WeaponPropertyJson> properties) {
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
}
