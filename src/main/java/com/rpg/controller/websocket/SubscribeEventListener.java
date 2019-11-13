package com.rpg.controller.websocket;

import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import com.rpg.service.application.ScenarioService;
import com.rpg.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class SubscribeEventListener {

    @Autowired private ScenarioService scenarioService;
    @Autowired private UserService userService;
    
//TODO necessary?
//    @EventListener
//    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
//        GenericMessage message = (GenericMessage) event.getMessage();
//        String simpDestination = (String) message.getHeaders().get("simpDestination");
//
//        if (simpDestination != null && simpDestination.startsWith("/ws/scenario/")) {
//            List<String> parts = Arrays.asList(simpDestination.split("/"));
//            String scenarioKey = null;
//            String player = null;
//            if(parts.size() > 3 && parts.get(2).equals("scenario")) scenarioKey = parts.get(3);
//            if(parts.size() > 5 && parts.get(4).equals("player")) player = parts.get(5);
//
//            if(scenarioKey != null && player != null)
//                subscribeToPrivateChannel(player, scenarioKey, event);
//        }
//    }
//
//    private void subscribeToPrivateChannel(String player, String scenarioKey, SessionSubscribeEvent event) {
//        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
//        User user = userService.findByUsername(player);
//
//        if(scenario == null || user == null){
//        }
//    }
}
