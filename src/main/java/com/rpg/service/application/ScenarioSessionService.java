package com.rpg.service.application;

import com.rpg.model.application.Scenario;
import com.rpg.model.application.ScenarioSession;
import com.rpg.model.application.ScenarioStatusType;
import com.rpg.model.application.UserSession;
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

    public void addUserToScenarioSession(String sessionId, String username, String scenarioKey){
        User user = userService.findByUsername(username);
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        ScenarioSession scenarioSession = scenarioSessions.get(scenarioKey);
        UserSession userSession = new UserSession(user, sessionId);
        if(Objects.nonNull(scenarioSession)) {
            Set<UserSession> usersInScenarioSession = scenarioSession.getUsersInSession();
            usersInScenarioSession.add(userSession);
        }
        else{
            scenarioSession = new ScenarioSession(scenario, ScenarioStatusType.STANDBY,
                    new HashSet<>(Collections.singletonList(userSession)));
            scenarioSessions.put(scenarioKey, scenarioSession);
        }

    }

    public void removeUserFromScenarioSession(String sessionId, String username, String scenarioKey){
        User user = userService.findByUsername(username);
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        ScenarioSession scenarioSession = scenarioSessions.get(scenarioKey);
        if(Objects.nonNull(scenarioSession)) {
            Set<UserSession> usersInScenarioSession = scenarioSession.getUsersInSession();
            UserSession userSession = findUserSessionWithUserAndSessionId(usersInScenarioSession, user, sessionId);
            if(Objects.nonNull(userSession))
                usersInScenarioSession.remove(userSession);

            if(usersInScenarioSession.isEmpty())
                scenarioSessions.remove(scenarioKey);
            else if(scenarioService.isUserGameMasterInScenario(user, scenario) &&
                    Objects.isNull(findUserSessionWithUserAndSessionId(usersInScenarioSession, user, sessionId)))
                changeScenarioSessionStatus(scenarioSession, ScenarioStatusType.STANDBY);
        }
        else
            LOGGER.info("User {} disconnected from scenario {} when he was not present in scenario session.", username, scenarioKey);
    }

    private UserSession findUserSessionWithUserAndSessionId(Set<UserSession> usersInScenarioSession, User user, String sessionId) {
        for(UserSession userSession : usersInScenarioSession){
            if(userSession.getSessionId().equals(sessionId) && userSession.getUser().getUsername().equals(user.getUsername()))
                return userSession;
        }
        return null;
    }

    public Set<User> getOnlineUsersFromScenario(Scenario scenario){
        ScenarioSession scenarioSession = scenarioSessions.get(scenario.getScenarioKey());
        if(Objects.isNull(scenarioSession)) return Collections.emptySet();
        Set<UserSession> userSessions = scenarioSession.getUsersInSession();
        Set<User> users = new HashSet<>();
        for(UserSession userSession : userSessions){
            users.add(userSession.getUser());
        }
        return users;
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
