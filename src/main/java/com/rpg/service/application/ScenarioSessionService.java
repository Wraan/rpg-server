package com.rpg.service.application;

import com.rpg.model.application.Scenario;
import com.rpg.model.application.ScenarioSession;
import com.rpg.model.application.ScenarioStatusType;
import com.rpg.model.security.User;
import com.rpg.service.security.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScenarioSessionService {

    @Autowired private UserService userService;
    @Autowired private ScenarioService scenarioService;

    private Map<String, ScenarioSession> scenarioSessions = new HashMap<>();

    private Logger LOGGER = LogManager.getLogger(getClass());

    public void addUserToScenarioSession(String username, String scenarioKey){
        User user = userService.findByUsername(username);
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        ScenarioSession scenarioSession = scenarioSessions.get(scenarioKey);
        if(Objects.nonNull(scenarioSession)) {
            Set<User> usersInScenarioSession = scenarioSession.getUsersInSession();
            usersInScenarioSession.add(user);
        }
        else{
            scenarioSession = new ScenarioSession(scenario, ScenarioStatusType.STANDBY,
                    new HashSet<>(Collections.singletonList(user)));
            scenarioSessions.put(scenarioKey, scenarioSession);
        }

    }

    public void removeUserFromScenarioSession(String username, String scenarioKey){
        User user = userService.findByUsername(username);
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        ScenarioSession scenarioSession = scenarioSessions.get(scenarioKey);
        if(Objects.nonNull(scenarioSession)) {
            Set<User> usersInScenarioSession = scenarioSession.getUsersInSession();
            usersInScenarioSession.remove(user);
            if(scenarioService.isUserGameMasterInScenario(user, scenario))
                changeScenarioSessionStatus(scenarioSession, ScenarioStatusType.STANDBY);
            if(usersInScenarioSession.isEmpty()) {
                scenarioSessions.remove(scenarioKey);
            }
        }
        else
            LOGGER.info("User {} disconnected from scenario {} when he was not present in scenario session.", username, scenarioKey);

    }

    public Set<User> getUsersFromScenarioSession(String scenarioKey){
        return scenarioSessions.get(scenarioKey).getUsersInSession();
    }

    public ScenarioSession getScenarioSession(Scenario scenario) {
        ScenarioSession scenarioSession = scenarioSessions.get(scenario.getScenarioKey());
        return Objects.isNull(scenarioSession) ?
                new ScenarioSession(scenario, ScenarioStatusType.STOPPED, Collections.emptySet())
                : scenarioSession;

    }

    public void removeScenarioSessionByScenario(Scenario scenario){
        scenarioSessions.remove(scenario.getScenarioKey());
    }

    public void changeScenarioSessionStatus(ScenarioSession scenarioSession, ScenarioStatusType type) {
        scenarioSession.setScenarioStatusType(type);
    }
}
