package com.rpg.model.dnd.character.equipment;

import com.rpg.model.dnd.equipment.Tool;

import javax.persistence.*;

@Entity
@Table(name = "character_tools")
public class ToolAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "tool_id")
    private Tool tool;
    @ManyToOne
    @JoinColumn(name = "character_equipment_id")
    private CharacterEquipment characterEquipment;
    private int amount;

    public ToolAmount() {
    }

    public ToolAmount(Tool tool, CharacterEquipment characterEquipment, int amount) {
        this.tool = tool;
        this.characterEquipment = characterEquipment;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public CharacterEquipment getCharacterEquipment() {
        return characterEquipment;
    }

    public void setCharacterEquipment(CharacterEquipment characterEquipment) {
        this.characterEquipment = characterEquipment;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
