package com.rpg.dto.application.character;

public class HealthDto {
    private int maxHealth;
    private int temporaryHealth;
    private int actualHealth;

    public HealthDto() {
    }

    public HealthDto(int maxHealth, int temporaryHealth, int actualHealth) {
        this.maxHealth = maxHealth;
        this.temporaryHealth = temporaryHealth;
        this.actualHealth = actualHealth;
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
