package com.rpg.dto.dnd.character;

public class HitDicesDto {
    private String dice;
    private int total;
    private int used;

    public HitDicesDto() {
    }

    public HitDicesDto(String dice, int total, int used) {
        this.dice = dice;
        this.total = total;
        this.used = used;
    }

    public String getDice() {
        return dice;
    }

    public void setDice(String dice) {
        this.dice = dice;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}
