package com.rpg.model.application;

import com.rpg.model.security.User;

import java.util.List;

public class ScenarioStatus {

    private Scenario scenario;
    private ScenarioStatusType scenarioStatusType;
    private List<UserSession> userSessions;

    public ScenarioStatus() {
    }

    public ScenarioStatus(Scenario scenario, List<UserSession> userSessions) {
        this.scenario = scenario;
        this.userSessions = userSessions;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public List<UserSession> getUserSessions() {
        return userSessions;
    }

    public void setUserSessions(List<UserSession> userSessions) {
        this.userSessions = userSessions;
    }
}
