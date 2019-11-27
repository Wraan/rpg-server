package com.rpg.dto.dnd.character;

public class SpellSlotDto {
    private int total;
    private int used;
    private int level;

    public SpellSlotDto() {
    }

    public SpellSlotDto(int total, int used, int level) {
        this.total = total;
        this.used = used;
        this.level = level;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
