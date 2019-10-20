package com.rpg.dto.dnd.equipment;

public class ArmorClassJson {

    private int base;
    private boolean dexBonus;
    private int maxBonus;

    public ArmorClassJson() {
    }

    public ArmorClassJson(int base, boolean dexBonus, int maxBonus) {
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
}
