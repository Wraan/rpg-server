package com.rpg.dto.dnd.character;

import java.util.List;
import java.util.Set;

public class CharacterSpellsResponse {

    private Set<String> spells;
    private List<SpellSlotDto> spellSlots;
    private int spellSaveDc;
    private String baseStat;
    private int spellAttackBonus;

    public CharacterSpellsResponse() {
    }

    public CharacterSpellsResponse(Set<String> spells, List<SpellSlotDto> spellSlots, int spellSaveDc, String baseStat, int spellAttackBonus) {
        this.spells = spells;
        this.spellSlots = spellSlots;
        this.spellSaveDc = spellSaveDc;
        this.baseStat = baseStat;
        this.spellAttackBonus = spellAttackBonus;
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
