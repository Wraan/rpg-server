package com.rpg.model.dnd.character.equipment;

import com.rpg.model.dnd.equipment.Weapon;

import javax.persistence.*;

@Entity
@Table(name = "character_weapons")
public class WeaponAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "gear_id")
    private Weapon weapon;
    @ManyToOne
    @JoinColumn(name = "character_equipment_id")
    private CharacterEquipment characterEquipment;
    private int amount;

    public WeaponAmount() {
    }

    public WeaponAmount(Weapon weapon, CharacterEquipment characterEquipment, int amount) {
        this.weapon = weapon;
        this.characterEquipment = characterEquipment;
        this.amount = amount;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public CharacterEquipment getCharacterEquipment() {
        return characterEquipment;
    }

    public void setCharacterEquipment(CharacterEquipment characterEquipment) {
        this.characterEquipment = characterEquipment;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
