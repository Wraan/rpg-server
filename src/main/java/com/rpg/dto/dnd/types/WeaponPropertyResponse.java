package com.rpg.dto.dnd.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeaponPropertyResponse {

    private long id;
    private String name;
    private String description;

    private boolean visible;
    private String creatorName;
    private String scenarioKey;

    public WeaponPropertyResponse() {
    }

    public WeaponPropertyResponse(long id, String name, String description, boolean visible, String creatorName, String scenarioKey) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creatorName = creatorName;
        this.visible = visible;
        this.scenarioKey = scenarioKey;
    }

    public String getScenarioKey() {
        return scenarioKey;
    }

    public void setScenarioKey(String scenarioKey) {
        this.scenarioKey = scenarioKey;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}
