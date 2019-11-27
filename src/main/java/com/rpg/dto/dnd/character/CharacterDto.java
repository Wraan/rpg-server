package com.rpg.dto.dnd.character;

public class CharacterDto {

    private String name;
    private String race;
    private String profession;
    private int level;
    private String background;
    private int experience;
    private String alignment;
    private int proficiency;
    private int passivePerception;
    private int passiveInsight;
    private int initiative;
    private String speed;
    private int inspiration;
    private AttributesDto attributes;
    private HealthDto health;
    private HitDicesDto hitDices;

    public CharacterDto() {
    }

    public int getPassiveInsight() {
        return passiveInsight;
    }

    public void setPassiveInsight(int passiveInsight) {
        this.passiveInsight = passiveInsight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public int getProficiency() {
        return proficiency;
    }

    public void setProficiency(int proficiency) {
        this.proficiency = proficiency;
    }

    public AttributesDto getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesDto attributes) {
        this.attributes = attributes;
    }

    public int getPassivePerception() {
        return passivePerception;
    }

    public void setPassivePerception(int passivePerception) {
        this.passivePerception = passivePerception;
    }

    public HealthDto getHealth() {
        return health;
    }

    public void setHealth(HealthDto health) {
        this.health = health;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public int getInspiration() {
        return inspiration;
    }

    public void setInspiration(int inspiration) {
        this.inspiration = inspiration;
    }

    public HitDicesDto getHitDices() {
        return hitDices;
    }

    public void setHitDices(HitDicesDto hitDices) {
        this.hitDices = hitDices;
    }
}
