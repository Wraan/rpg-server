package com.rpg.model.dnd.abilities;

import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;

import javax.persistence.*;

@Entity
@Table(name = "languages")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String type;
    private String script;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    @ManyToOne
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;

    public Language() {
    }

    public Language(String name, String type, String script) {
        this.name = name;
        this.type = type;
        this.script = script;
    }

    public Language(String name, String type, String script, User creator, Scenario scenario) {
        this.name = name;
        this.type = type;
        this.script = script;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
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
