package com.rpg.model.application;

import com.rpg.model.security.User;

import java.util.List;
import java.util.Set;

public class ScenarioSession {

    private Scenario scenario;
    private ScenarioStatusType scenarioStatusType;
    private Set<UserSession> usersInSession;

    public ScenarioSession() {
    }

    public ScenarioSession(Scenario scenario, ScenarioStatusType scenarioStatusType, Set<UserSession> usersInSession) {
        this.scenario = scenario;
        this.scenarioStatusType = scenarioStatusType;
        this.usersInSession = usersInSession;
    }

    public ScenarioStatusType getScenarioStatusType() {
        return scenarioStatusType;
    }

    public void setScenarioStatusType(ScenarioStatusType scenarioStatusType) {
        this.scenarioStatusType = scenarioStatusType;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public Set<UserSession> getUsersInSession() {
        return usersInSession;
    }

    public void setUsersInSession(Set<UserSession> usersInSession) {
        this.usersInSession = usersInSession;
    }
}
