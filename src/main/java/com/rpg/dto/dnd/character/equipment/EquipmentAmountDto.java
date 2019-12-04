package com.rpg.dto.dnd.character.equipment;

public class EquipmentAmountDto {
    private String name;
    private int amount;

    public EquipmentAmountDto() {
    }

    public EquipmentAmountDto(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
