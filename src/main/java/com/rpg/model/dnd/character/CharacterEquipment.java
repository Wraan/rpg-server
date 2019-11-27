package com.rpg.model.dnd.character;

import com.rpg.model.dnd.equipment.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "character_equipment")
public class CharacterEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int armorClass;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "character_armors", joinColumns = {@JoinColumn(name = "character_equipment_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "armor_id", referencedColumnName = "id")})
    private Set<Armor> armors;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "character_gear", joinColumns = {@JoinColumn(name = "character_equipment_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "gear_id", referencedColumnName = "id")})
    private Set<Gear> gear;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "character_vehicles", joinColumns = {@JoinColumn(name = "character_equipment_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "vehicle_id", referencedColumnName = "id")})
    private Set<Vehicle> vehicles;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "character_tools", joinColumns = {@JoinColumn(name = "character_equipment_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tool_id", referencedColumnName = "id")})
    private Set<Tool> tools;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "character_weapons", joinColumns = {@JoinColumn(name = "character_equipment_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "weapon_id", referencedColumnName = "id")})
    private Set<Weapon> weapons;
    @OneToMany(mappedBy = "characterEquipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Attack> attacks;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "currencies_id", referencedColumnName = "id")
    private Currency currency = new Currency();
    @OneToOne
    @JoinColumn(name = "character_id", referencedColumnName = "id")
    private Character character;

    public CharacterEquipment() {
    }

    public CharacterEquipment(int armorClass, Set<Armor> armors, Set<Gear> gear, Set<Vehicle> vehicles,
                              Set<Tool> tools, Set<Weapon> weapons, Set<Attack> attacks, Currency currency) {
        this.armorClass = armorClass;
        this.armors = armors;
        this.gear = gear;
        this.vehicles = vehicles;
        this.tools = tools;
        this.weapons = weapons;
        this.attacks = attacks;
        this.currency = currency;
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

    public Set<Armor> getArmors() {
        return armors;
    }

    public void setArmors(Set<Armor> armors) {
        this.armors = armors;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Set<Tool> getTools() {
        return tools;
    }

    public void setTools(Set<Tool> tools) {
        this.tools = tools;
    }

    public Set<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(Set<Weapon> weapons) {
        this.weapons = weapons;
    }

    public Set<Gear> getGear() {
        return gear;
    }

    public void setGear(Set<Gear> gear) {
        this.gear = gear;
    }

    public Set<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(Set<Attack> attacks) {
        this.attacks = attacks;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
