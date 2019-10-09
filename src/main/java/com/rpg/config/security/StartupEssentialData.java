package com.rpg.config.security;

import com.rpg.model.security.OAuthClientDetails;
import com.rpg.model.security.Role;
import com.rpg.model.security.User;
import com.rpg.repository.OAuthClientDetailsRepository;
import com.rpg.service.MyUserDetailsService;
import com.rpg.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@EnableScheduling
public class StartupEssentialData implements ApplicationRunner {

    @Autowired
    private OAuthClientDetailsRepository oauthClientDetailsRepository;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private RoleService roleService;

    @Lazy
    @Autowired
    private SimpMessagingTemplate template;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        addEssentialRoles();
        addEssentialClients();
        addEssentialUsers();
    }

    private void addEssentialRoles(){
        List<Role> roles = Arrays.asList(
                new Role("ROLE_ADMIN"),
                new Role("ROLE_CLIENT"),
                new Role("ROLE_USER")
        );
        roleService.saveAll(roles);
    }

    private void addEssentialClients(){
        List<OAuthClientDetails> clientDetails = Arrays.asList(
                new OAuthClientDetails("web", "{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG",
                        "READ,WRITE,EXECUTE", 3600, 10000, "api-resource",
                        "authorization_code,password,refresh_token,implicit", roleService.findByName("CLIENT").getName())
        );
        oauthClientDetailsRepository.saveAll(clientDetails);
    }

    private void addEssentialUsers(){
        List<User> users = Arrays.asList(
                new User("admin", "{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG",
                        "admin@admin.com", true, true, true,
                        true, Arrays.asList(roleService.findByName("ADMIN"), roleService.findByName("USER"))),
                new User("test", "{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG",
                        "test@test.com", true, true, true,
                        true, Arrays.asList(roleService.findByName("USER")))
        );
        userDetailsService.saveAll(users);
    }

    @Scheduled(fixedDelay = 5000)
    public void sendMessage(){
        template.convertAndSend("/ws/message", "If you see this message very 5 seconds " +
                "everything is working fine :)");

    }
}
