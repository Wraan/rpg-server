package com.rpg.config.startup.essentialData;

import com.rpg.model.application.Scenario;
import com.rpg.service.application.ScenarioService;
import com.rpg.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Order(3)
public class TestData implements ApplicationRunner {

    @Lazy @Autowired private SimpMessagingTemplate template;
    @Autowired private ScenarioService scenarioService;
    @Autowired private UserService userService;

    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createTestScenario();
    }

    private void createTestScenario(){
        Scenario scenario = scenarioService.findByScenarioKey("TESTSCEN");
        if(scenario == null) {
            scenario = new Scenario("TESTSCEN", passwordEncoder.encode("password"),
                    userService.findByUsername("admin"), Collections.emptyList(), "Test scenario");
            scenarioService.save(scenario);
        }
    }

    @Scheduled(fixedDelay = 5000)
    public void sendMessage(){
        template.convertAndSend("/ws/message", "If you see this message every 5 seconds " +
                "everything is working fine :)");

    }
}
