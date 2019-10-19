package com.rpg.model.dnd.abilities;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.types.MagicSchool;
import com.rpg.model.security.User;

import javax.persistence.*;

@Entity
@Table(name = "spells")
public class Spell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    @Column(length = 4095)
    private String description;
    @Column(length = 1023)
    private String higherLevels;
    private int level;
    private String range;
    private String components;
    @Column(length = 1023)
    private String material;
    private boolean ritual;
    private String duration;
    private boolean concentration;
    private String castingTime;
    @ManyToOne
    @JoinColumn(name = "magic_school_id")
    private MagicSchool magicSchool;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    @ManyToOne
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;


    public Spell() {
    }

    public Spell(long id, String name, String description, String higherLevels, int level, String range,
                 String components, String material, boolean ritual, String duration, boolean concentration,
                 String castingTime, MagicSchool magicSchool) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.higherLevels = higherLevels;
        this.level = level;
        this.range = range;
        this.components = components;
        this.material = material;
        this.ritual = ritual;
        this.duration = duration;
        this.concentration = concentration;
        this.castingTime = castingTime;
        this.magicSchool = magicSchool;
    }

    public Spell(String name, String description, String higherLevels, int level, String range, String components,
                 String material, boolean ritual, String duration, boolean concentration, String castingTime,
                 MagicSchool magicSchool, User creator, Scenario scenario) {
        this.name = name;
        this.description = description;
        this.higherLevels = higherLevels;
        this.level = level;
        this.range = range;
        this.components = components;
        this.material = material;
        this.ritual = ritual;
        this.duration = duration;
        this.concentration = concentration;
        this.castingTime = castingTime;
        this.magicSchool = magicSchool;
        this.creator = creator;
        this.scenario = scenario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public MagicSchool getMagicSchool() {
        return magicSchool;
    }

    public void setMagicSchool(MagicSchool magicSchool) {
        this.magicSchool = magicSchool;
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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }
}
