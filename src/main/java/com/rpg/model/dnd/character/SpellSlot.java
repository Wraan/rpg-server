package com.rpg.model.dnd.character;

import javax.persistence.*;

@Entity
@Table(name = "spell_slots")
public class SpellSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int total;
    private int used;
    private int level;
    @ManyToOne
    @JoinColumn(name = "character_spells_id")
    private CharacterSpells characterSpells;

    public SpellSlot() {
    }

    public SpellSlot(int total, int used, int level, CharacterSpells characterSpells) {
        this.total = total;
        this.used = used;
        this.level = level;
        this.characterSpells = characterSpells;
    }

    public CharacterSpells getCharacterSpells() {
        return characterSpells;
    }

    public void setCharacterSpells(CharacterSpells characterSpells) {
        this.characterSpells = characterSpells;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
