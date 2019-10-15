package com.rpg.model.dnd.equipment;

import javax.persistence.*;

@Entity
@Table(name = "armors")
public class Armor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "armor_class_id", referencedColumnName = "id")
    private ArmorClass armorClass;
    private int strMinimum;
    private boolean stealthDisadvantage;
    private int weight;
    private String cost;

    public Armor() {
    }

    public Armor(String name, ArmorClass armorClass, int strMinimum, boolean stealthDisadvantage,
                 int weight, String cost) {
        this.name = name;
        this.armorClass = armorClass;
        this.strMinimum = strMinimum;
        this.stealthDisadvantage = stealthDisadvantage;
        this.weight = weight;
        this.cost = cost;
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

    public ArmorClass getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(ArmorClass armorClass) {
        this.armorClass = armorClass;
    }

    public int getStrMinimum() {
        return strMinimum;
    }

    public void setStrMinimum(int strMinimum) {
        this.strMinimum = strMinimum;
    }

    public boolean isStealthDisadvantage() {
        return stealthDisadvantage;
    }

    public void setStealthDisadvantage(boolean stealthDisadvantage) {
        this.stealthDisadvantage = stealthDisadvantage;
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
