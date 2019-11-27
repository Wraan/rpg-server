package com.rpg.model.dnd.character;

import com.rpg.model.dnd.abilities.Spell;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "character_spells")
public class CharacterSpells {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "character_spells_mappings", joinColumns = {@JoinColumn(name = "spell_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "character_spell_id", referencedColumnName = "id")})
    private Set<Spell> spells;
    @OneToMany(mappedBy = "characterSpells", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SpellSlot> spellSlots = new HashSet<>();
    private int spellSaveDc;
    private String baseStat;
    private int spellAttackBonus;

    public CharacterSpells() {
    }

    public CharacterSpells(Set<Spell> spells, Set<SpellSlot> spellSlots, int spellSaveDc, String baseStat, int spellAttackBonus) {
        this.spells = spells;
        this.spellSlots = spellSlots;
        this.spellSaveDc = spellSaveDc;
        this.baseStat = baseStat;
        this.spellAttackBonus = spellAttackBonus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Spell> getSpells() {
        return spells;
    }

    public void setSpells(Set<Spell> spells) {
        this.spells = spells;
    }

    public Set<SpellSlot> getSpellSlots() {
        return spellSlots;
    }

    public void setSpellSlots(Set<SpellSlot> spellSlots) {
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
