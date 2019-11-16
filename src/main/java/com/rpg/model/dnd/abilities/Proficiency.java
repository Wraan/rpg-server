package com.rpg.model.dnd.abilities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;

import javax.persistence.*;

@Entity
@Table(name = "proficiencies")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Proficiency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String type;
    private boolean visible;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    @ManyToOne
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;

    public Proficiency() {
    }

    public Proficiency(String name, String type) {
        this.name = name;
        this.type = type;
        this.visible = true;
    }

    public Proficiency(String name, String type, boolean visible, User creator, Scenario scenario) {
        this.name = name;
        this.type = type;
        this.visible = visible;
        this.creator = creator;
        this.scenario = scenario;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
