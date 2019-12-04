package com.rpg.model.dnd.character.equipment;

import com.rpg.model.dnd.character.Character;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "character_equipment")
public class CharacterEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int armorClass;
    @OneToMany(mappedBy = "characterEquipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArmorAmount> armors;
    @OneToMany(mappedBy = "characterEquipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GearAmount> gear;
    @OneToMany(mappedBy = "characterEquipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehicleAmount> vehicles;
    @OneToMany(mappedBy = "characterEquipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ToolAmount> tools;
    @OneToMany(mappedBy = "characterEquipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeaponAmount> weapons;
    @OneToMany(mappedBy = "characterEquipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attack> attacks;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "currencies_id", referencedColumnName = "id")
    private Currency currency = new Currency();
    @OneToOne
    @JoinColumn(name = "character_id", referencedColumnName = "id")
    private Character character;

    public CharacterEquipment() {
    }

    public CharacterEquipment(int armorClass, List<ArmorAmount> armors, List<GearAmount> gear,
                              List<VehicleAmount> vehicles, List<ToolAmount> tools, List<WeaponAmount> weapons,
                              List<Attack> attacks, Currency currency, Character character) {
        this.armorClass = armorClass;
        this.armors = armors;
        this.gear = gear;
        this.vehicles = vehicles;
        this.tools = tools;
        this.weapons = weapons;
        this.attacks = attacks;
        this.currency = currency;
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public List<ArmorAmount> getArmors() {
        return armors;
    }

    public void setArmors(List<ArmorAmount> armors) {
        this.armors = armors;
    }

    public List<GearAmount> getGear() {
        return gear;
    }

    public void setGear(List<GearAmount> gear) {
        this.gear = gear;
    }

    public List<VehicleAmount> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleAmount> vehicles) {
        this.vehicles = vehicles;
    }

    public List<ToolAmount> getTools() {
        return tools;
    }

    public void setTools(List<ToolAmount> tools) {
        this.tools = tools;
    }

    public List<WeaponAmount> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<WeaponAmount> weapons) {
        this.weapons = weapons;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(List<Attack> attacks) {
        this.attacks = attacks;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
