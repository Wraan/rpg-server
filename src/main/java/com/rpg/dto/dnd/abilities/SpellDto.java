package com.rpg.dto.dnd.abilities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpellDto {

    private String name;
    private String description;
    private String higherLevels;
    private int level;
    private String range;
    private String components;
    private String material;
    private boolean ritual;
    private String duration;
    private boolean concentration;
    private String castingTime;
    private String magicSchool;
    private boolean visible;

    public SpellDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHigherLevels() {
        return higherLevels;
    }

    public void setHigherLevels(String higherLevels) {
        this.higherLevels = higherLevels;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public boolean isRitual() {
        return ritual;
    }

    public void setRitual(boolean ritual) {
        this.ritual = ritual;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isConcentration() {
        return concentration;
    }

    public void setConcentration(boolean concentration) {
        this.concentration = concentration;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public void setCastingTime(String castingTime) {
        this.castingTime = castingTime;
    }

    public String getMagicSchool() {
        return magicSchool;
    }

    public void setMagicSchool(String magicSchool) {
        this.magicSchool = magicSchool;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
