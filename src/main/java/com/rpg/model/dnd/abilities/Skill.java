package com.rpg.model.dnd.abilities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;

import javax.persistence.*;

@Entity
@Table(name = "skills")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(length = 4095)
    private String description;
    private String abilityScore;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    @ManyToOne
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;

    public Skill() {
    }

    public Skill(String name, String description, String abilityScore) {
        this.name = name;
        this.description = description;
        this.abilityScore = abilityScore;
    }

    public Skill(String name, String description, String abilityScore, User creator, Scenario scenario) {
        this.name = name;
        this.description = description;
        this.abilityScore = abilityScore;
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

    public String getAbilityScore() {
        return abilityScore;
    }

    public void setAbilityScore(String abilityScore) {
        this.abilityScore = abilityScore;
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
