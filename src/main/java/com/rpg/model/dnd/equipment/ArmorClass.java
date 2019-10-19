package com.rpg.model.dnd.equipment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "armor_classes")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArmorClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int base;
    private boolean dexBonus;
    private int maxBonus;

    public ArmorClass() {
    }

    public ArmorClass(int base, boolean dexBonus, int maxBonus) {
        this.base = base;
        this.dexBonus = dexBonus;
        this.maxBonus = maxBonus;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public boolean isDexBonus() {
        return dexBonus;
    }

    public void setDexBonus(boolean dexBonus) {
        this.dexBonus = dexBonus;
    }

    public int getMaxBonus() {
        return maxBonus;
    }

    public void setMaxBonus(int maxBonus) {
        this.maxBonus = maxBonus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
