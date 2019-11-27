package com.rpg.dto.dnd.character;

import java.util.List;
import java.util.Set;

public class CharacterSpellsDto {
    private String name;
    private Set<String> spells;
    private List<SpellSlotDto> spellSlots;
    private int spellSaveDc;
    private String baseStat;
    private int spellAttackBonus;

    public CharacterSpellsDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getSpells() {
        return spells;
    }

    public void setSpells(Set<String> spells) {
        this.spells = spells;
    }

    public List<SpellSlotDto> getSpellSlots() {
        return spellSlots;
    }

    public void setSpellSlots(List<SpellSlotDto> spellSlots) {
        this.spellSlots = spellSlots;
    }

    public int getSpellSaveDc() {
        return spellSaveDc;
    }

    public void setSpellSaveDc(int spellSaveDc) {
        this.spellSaveDc = spellSaveDc;
    }

    public String getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(String baseStat) {
        this.baseStat = baseStat;
    }

    public int getSpellAttackBonus() {
        return spellAttackBonus;
    }

    public void setSpellAttackBonus(int spellAttackBonus) {
        this.spellAttackBonus = spellAttackBonus;
    }
}
