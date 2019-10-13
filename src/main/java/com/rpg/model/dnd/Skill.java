package com.rpg.model.dnd;

import javax.persistence.*;

@Entity
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(length = 4095)
    private String description;
    private String abilityScore;

    public Skill() {
    }

    public Skill(String name, String description, String abilityScore) {
        this.name = name;
        this.description = description;
        this.abilityScore = abilityScore;
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
}
