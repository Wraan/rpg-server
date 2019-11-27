package com.rpg.dto.dnd.character;

public class AttackDto {

    private String name;
    private int bonus;
    private String damage;
    private String type;

    public AttackDto() {
    }

    public AttackDto(String name, int bonus, String damage, String type) {
        this.name = name;
        this.bonus = bonus;
        this.damage = damage;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
