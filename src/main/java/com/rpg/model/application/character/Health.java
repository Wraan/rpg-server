package com.rpg.model.application.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "health")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Health {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int maxHealth;
    private int temporaryHealth;
    private int actualHealth;

    public Health() {
    }

    public Health(int maxHealth, int temporaryHealth, int actualHealth) {
        this.maxHealth = maxHealth;
        this.temporaryHealth = temporaryHealth;
        this.actualHealth = actualHealth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getTemporaryHealth() {
        return temporaryHealth;
    }

    public void setTemporaryHealth(int temporaryHealth) {
        this.temporaryHealth = temporaryHealth;
    }

    public int getActualHealth() {
        return actualHealth;
    }

    public void setActualHealth(int actualHealth) {
        this.actualHealth = actualHealth;
    }
}
