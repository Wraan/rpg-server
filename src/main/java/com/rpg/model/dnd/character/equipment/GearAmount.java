package com.rpg.model.dnd.character.equipment;

import com.rpg.model.dnd.equipment.Armor;
import com.rpg.model.dnd.equipment.Gear;

import javax.persistence.*;

@Entity
@Table(name = "character_gear")
public class GearAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "gear_id")
    private Gear gear;
    @ManyToOne
    @JoinColumn(name = "character_equipment_id")
    private CharacterEquipment characterEquipment;
    private int amount;

    public GearAmount() {
    }

    public GearAmount(Gear gear, CharacterEquipment characterEquipment, int amount) {
        this.gear = gear;
        this.characterEquipment = characterEquipment;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Gear getGear() {
        return gear;
    }

    public void setGear(Gear gear) {
        this.gear = gear;
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
