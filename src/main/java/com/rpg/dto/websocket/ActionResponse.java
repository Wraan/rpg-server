package com.rpg.dto.websocket;

public class ActionResponse {

    private String action;
    private MessageResponse body;

    public ActionResponse() {
    }

    public ActionResponse(String action, MessageResponse body) {
        this.action = action;
        this.body = body;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public MessageResponse getBody() {
        return body;
    }

    public void setBody(MessageResponse body) {
        this.body = body;
    }
}
