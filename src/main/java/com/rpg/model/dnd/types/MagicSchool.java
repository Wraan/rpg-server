package com.rpg.model.dnd.types;

import javax.persistence.*;

@Entity
@Table(name = "magic_schools")
public class MagicSchool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    @Column(length = 4095)
    private String description;

    public MagicSchool() {
    }

    public MagicSchool(String name, String description) {
        this.name = name;
        this.description = description;
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
