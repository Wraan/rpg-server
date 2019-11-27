package com.rpg.dto.websocket;

public class DiceRollDto {
    private int dices;
    private int value;
    private String characterName;
    private boolean visible;

    public DiceRollDto() {
    }

    public int getDices() {
        return dices;
    }

    public void setDices(int dices) {
        this.dices = dices;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCharacterName() {
        return characterName;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}
