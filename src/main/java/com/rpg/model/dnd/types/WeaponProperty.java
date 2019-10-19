package com.rpg.model.dnd.types;

import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;

import javax.persistence.*;

@Entity
@Table(name = "weapon_properties")
public class WeaponProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(length = 4095)
    private String description;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    @ManyToOne
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;

    public WeaponProperty() {
    }

    public WeaponProperty(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public WeaponProperty(String name, String description, User creator, Scenario scenario) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.scenario = scenario;
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
}
