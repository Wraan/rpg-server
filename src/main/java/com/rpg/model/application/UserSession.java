package com.rpg.model.application;

import com.rpg.model.security.User;

public class UserSession {
    private User user;
    private String sessionId;

    public UserSession() {
    }

    public UserSession(User user, String sessionId) {
        this.user = user;
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
