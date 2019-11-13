package com.rpg.service.application;

import com.rpg.model.application.Scenario;
import com.rpg.model.application.ScenarioStatus;
import com.rpg.model.application.ScenarioStatusType;
import com.rpg.model.application.UserSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScenarioStatusService {

    private List<ScenarioStatus> scenarioStatuses = new ArrayList<>();

    public ScenarioStatus getScenarioStatus(Scenario scenario) {
        for(ScenarioStatus scenarioStatus: scenarioStatuses) {
            if (scenarioStatus.getScenario().getScenarioKey().equals(scenario.getScenarioKey()))
                return scenarioStatus;
        }
        ScenarioStatus newScenarioStatus = new ScenarioStatus(scenario, ScenarioStatusType.STOPPED,
                new ArrayList<UserSession>());
        scenarioStatuses.add(newScenarioStatus);
        return newScenarioStatus;
    }

    public void addScenarioStatus(ScenarioStatus scenarioStatus){
        scenarioStatuses.add(scenarioStatus);
    }

    public void removeScenarioStatus(ScenarioStatus scenarioStatus){
        scenarioStatuses.remove(scenarioStatus);
    }

    public void removeScenarioStatusByScenario(Scenario scenario){
        List<ScenarioStatus> toRemove = new ArrayList<>();
        for(ScenarioStatus scenarioStatus: scenarioStatuses) {
            if (scenarioStatus.getScenario().getScenarioKey().equals(scenario.getScenarioKey()))
                toRemove.add(scenarioStatus);
        }
        scenarioStatuses.removeAll(toRemove);
    }

    public void changeScenarioStatus(ScenarioStatus scenarioStatus, ScenarioStatusType type) {
        scenarioStatus.setScenarioStatusType(type);
    }
}
