package com.rpg.model.dnd.character.equipment;

import com.rpg.model.dnd.equipment.Armor;

import javax.persistence.*;

@Entity
@Table(name = "character_armors")
public class ArmorAmount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "armor_id")
    private Armor armor;
    @ManyToOne
    @JoinColumn(name = "character_equipment_id")
    private CharacterEquipment characterEquipment;
    private int amount;

    public ArmorAmount(Armor armor, CharacterEquipment characterEquipment, int amount) {
        this.characterEquipment = characterEquipment;
        this.armor = armor;
        this.amount = amount;
    }

    public ArmorAmount() {
    }

    public CharacterEquipment getCharacterEquipment() {
        return characterEquipment;
    }

    public void setCharacterEquipment(CharacterEquipment characterEquipment) {
        this.characterEquipment = characterEquipment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
