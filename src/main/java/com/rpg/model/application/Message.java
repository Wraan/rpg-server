package com.rpg.model.application;

import com.rpg.model.security.User;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private String sender;
    private MessageType type;
    private String whisperTarget;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;

    public Message() {
    }

    public Message(String content, String sender, MessageType type, String whisperTarget,
                   User user, Scenario scenario) {
        this.scenario = scenario;
        this.content = content;
        this.user = user;
        this.sender = sender;
        this.type = type;
        this.whisperTarget = whisperTarget;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getWhisperTarget() {
        return whisperTarget;
    }

    public void setWhisperTarget(String whisperTarget) {
        this.whisperTarget = whisperTarget;
    }
}
