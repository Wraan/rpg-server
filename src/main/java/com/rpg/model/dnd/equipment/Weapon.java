package com.rpg.model.dnd.equipment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.types.DamageType;
import com.rpg.model.dnd.types.WeaponProperty;
import com.rpg.model.security.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "weapons")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String category;
    private String weaponRange;
    private String damageDice;
    private int damageBonus;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "damage_type_id")
    private DamageType damageType;
    private int normalRange;
    private int longRange;
    private int normalThrowRange;
    private int longThrowRange;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "weapons_weapon_properties",
            joinColumns = { @JoinColumn(name = "weapon_id") },
            inverseJoinColumns = { @JoinColumn(name = "weapon_property_id") })
    private List<WeaponProperty> properties;
    private int weight;
    private String cost;
    private boolean visible;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    @ManyToOne
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;

    public Weapon() {
    }

    public Weapon(String name, String category, String weaponRange, String damageDice, int damageBonus,
                  DamageType damageType, int normalRange, int longRange, int normalThrowRange, int longThrowRange,
                  List<WeaponProperty> properties, int weight, String cost) {
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
        this.visible = true;
    }

    public Weapon(String name, String category, String weaponRange, String damageDice, int damageBonus, DamageType damageType, int normalRange, int longRange, int normalThrowRange, int longThrowRange, List<WeaponProperty> properties, int weight, String cost, boolean visible, User creator, Scenario scenario) {
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
        this.creator = creator;
        this.scenario = scenario;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
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

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
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

    public List<WeaponProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<WeaponProperty> properties) {
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

    public String getWeaponRange() {
        return weaponRange;
    }

    public void setWeaponRange(String weaponRange) {
        this.weaponRange = weaponRange;
    }
}
