package com.rpg.config.websocket;

import com.rpg.service.application.ScenarioService;
import com.rpg.service.application.ScenarioSessionService;
import com.rpg.service.security.JsonWebTokenAuthenticationService;
import com.rpg.service.security.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;
import java.util.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private Logger LOGGER = LogManager.getLogger(getClass());

    @Autowired private JsonWebTokenAuthenticationService authenticationService;
    @Autowired private UserService userService;
    @Autowired private ScenarioService scenarioService;
    @Autowired private ScenarioSessionService scenarioSessionService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/ws");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/rpg-server").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {

                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if(Objects.isNull(accessor)) return null;

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String authToken = accessor.getFirstNativeHeader("X-Authorization");
                    Principal principal = authenticationService.getUserFromToken(authToken);

                    if (Objects.isNull(principal)) return null;

                    accessor.setUser(principal);

                } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                    Principal principal = accessor.getUser();
                    String scenarioKey = (String) accessor.getSessionAttributes().get("scenarioKey");
                    if(Objects.isNull(principal) || Objects.isNull(scenarioKey)) return null;

                    scenarioSessionService.removeUserFromScenarioSession(accessor.getSessionId(), principal.getName(), scenarioKey);
                    LOGGER.info("Player {} in scenario {} with session {} has been disconnected",
                            principal.getName(), scenarioKey, accessor.getSessionId());

                } else if(StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                    Principal principal = accessor.getUser();
                    if(Objects.isNull(principal)) return null;
                    String destination = accessor.getDestination();

                    if (Objects.nonNull(destination) && destination.startsWith("/ws/scenario")) {
                        String scenarioKey = getScenarioKeyFromDestination(destination);
                        String playerName = getPlayerNameFromDestination(destination);
                        if(Objects.nonNull(scenarioKey) && Objects.nonNull(playerName)
                                && playerName.equals(principal.getName())){
                            accessor.getSessionAttributes().put("scenarioKey", scenarioKey);
                            scenarioSessionService.addUserToScenarioSession(accessor.getSessionId(), principal.getName(), scenarioKey);
                            LOGGER.info("Player {} subscribed to scenario {} in session {}", playerName,
                                    scenarioKey, accessor.getSessionId());
                        }
                    }
                }
                else if(StompCommand.UNSUBSCRIBE.equals(accessor.getCommand())) {
                    Principal principal = accessor.getUser();
                    String destination = accessor.getDestination();

                    LOGGER.info("Player {} unsubscribed from destination {} with session", principal.getName(),
                            destination, accessor.getSessionId());
                }
                return message;
            }
        });
    }
    private String getScenarioKeyFromDestination(String destination) {
        List<String> parts = Arrays.asList(destination.split("/"));
        if(parts.size() != 6) return null;
        String scenarioKey = parts.get(3);
        return scenarioService.existsByScenarioKey(scenarioKey) ? scenarioKey : null;
    }

    private String getPlayerNameFromDestination(String destination) {
        List<String> parts = Arrays.asList(destination.split("/"));
        if(parts.size() != 6) return null;
        String playerName = parts.get(5);
        return userService.existByUsername(playerName) ? playerName : null;
    }


}
