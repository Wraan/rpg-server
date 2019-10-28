package com.rpg.dto.websocket;

public class MessageResponse {

    private String content;
    private String sender;
    private String type;
    private String whisperTarget;

    public MessageResponse() {
    }

    public MessageResponse(String content, String sender, String type, String whisperTarget) {
        this.content = content;
        this.sender = sender;
        this.type = type;
        this.whisperTarget = whisperTarget;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWhisperTarget() {
        return whisperTarget;
    }

    public void setWhisperTarget(String whisperTarget) {
        this.whisperTarget = whisperTarget;
    }
}
