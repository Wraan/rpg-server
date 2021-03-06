package com.rpg.dto.dnd.abilities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LanguageResponse {
    private long id;
    private String name;
    private String type;
    private String script;
    private boolean visible;
    private String creatorName;
    private String scenarioKey;

    public LanguageResponse() {
    }

    public LanguageResponse(long id, String name, String type, String script, boolean visible, String creatorName, String scenarioKey) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.script = script;
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
