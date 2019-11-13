package com.rpg.service.application;

import com.rpg.model.application.Scenario;
import com.rpg.model.application.ScenarioStatus;
import com.rpg.model.application.ScenarioStatusType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScenarioStatusService {

    List<ScenarioStatus> scenarioStatuses = new ArrayList<>();

    public ScenarioStatusType getScenarioStatus(Scenario scenario) {
        //TODO end it
        return ScenarioStatusType.STOPPED;
    }
}
