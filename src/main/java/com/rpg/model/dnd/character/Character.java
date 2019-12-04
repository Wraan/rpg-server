package com.rpg.model.dnd.character;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.character.equipment.CharacterEquipment;
import com.rpg.model.security.User;

import javax.persistence.*;

@Entity
@Table(name = "characters")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 25)
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attributes_id", referencedColumnName = "id")
    private Attributes attributes;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "health_id", referencedColumnName = "id")
    private Health health;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hit_dices_id", referencedColumnName = "id")
    private HitDices hitDices;
    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "character_abilities_id", referencedColumnName = "id")
    private CharacterAbilities abilities;
    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "character_spells_id", referencedColumnName = "id")
    private CharacterSpells spells;
    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "character_equipment_id", referencedColumnName = "id")
    private CharacterEquipment equipment;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
    @ManyToOne
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;

    public Character() {
    }

    public Character(String name, String race, String profession, int level, String background, int experience,
                     String alignment, int proficiency, int passivePerception, int passiveInsight, int initiative, String speed,
                     int inspiration, Attributes attributes, Health health, HitDices hitDices, User owner,
                     Scenario scenario) {
        this.name = name;
        this.race = race;
        this.profession = profession;
        this.level = level;
        this.background = background;
        this.experience = experience;
        this.alignment = alignment;
        this.proficiency = proficiency;
        this.passivePerception = passivePerception;
        this.passiveInsight = passiveInsight;
        this.initiative = initiative;
        this.speed = speed;
        this.inspiration = inspiration;
        this.attributes = attributes;
        this.health = health;
        this.hitDices = hitDices;
        this.owner = owner;
        this.scenario = scenario;
    }

    public int getPassiveInsight() {
        return passiveInsight;
    }

    public void setPassiveInsight(int passiveInsight) {
        this.passiveInsight = passiveInsight;
    }

    public CharacterAbilities getAbilities() {
        return abilities;
    }

    public void setAbilities(CharacterAbilities abilities) {
        this.abilities = abilities;
    }

    public CharacterSpells getSpells() {
        return spells;
    }

    public void setSpells(CharacterSpells spells) {
        this.spells = spells;
    }

    public CharacterEquipment getEquipment() {
        return equipment;
    }

    public void setEquipment(CharacterEquipment equipment) {
        this.equipment = equipment;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
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

    public int getPassivePerception() {
        return passivePerception;
    }

    public void setPassivePerception(int passivePerception) {
        this.passivePerception = passivePerception;
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

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Health getHealth() {
        return health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }

    public HitDices getHitDices() {
        return hitDices;
    }

    public void setHitDices(HitDices hitDices) {
        this.hitDices = hitDices;
    }
}
