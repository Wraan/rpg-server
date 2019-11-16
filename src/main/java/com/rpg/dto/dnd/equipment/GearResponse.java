package com.rpg.dto.dnd.equipment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GearResponse {
    private long id;
    private String name;
    private String description;
    private int weight;
    private String cost;
    private boolean visible;
    private String creatorName;
    private String scenarioKey;


    public GearResponse() {
    }

    public GearResponse(long id, String name, String description, int weight, String cost, boolean visible, String creatorName, String scenarioKey) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.cost = cost;
        this.visible = visible;
        this.creatorName = creatorName;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}
