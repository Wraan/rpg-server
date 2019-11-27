package com.rpg.model.dnd.character;

import com.rpg.model.dnd.types.DamageType;

import javax.persistence.*;

@Entity
@Table(name = "attacks")
public class Attack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private int bonus;
    private String damage;
    @ManyToOne
    @JoinColumn(name = "damage_type_id")
    private DamageType damageType;

    @ManyToOne
    @JoinColumn(name = "character_equipment_id")
    private CharacterEquipment characterEquipment;

    public Attack() {
    }

    public Attack(String name, int bonus, String damage, DamageType damageType, CharacterEquipment characterEquipment) {
        this.name = name;
        this.bonus = bonus;
        this.damage = damage;
        this.damageType = damageType;
        this.characterEquipment = characterEquipment;
    }

    public long getId() {
        return id;
    }

    public CharacterEquipment getCharacterEquipment() {
        return characterEquipment;
    }

    public void setCharacterEquipment(CharacterEquipment characterEquipment) {
        this.characterEquipment = characterEquipment;
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

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }
}
