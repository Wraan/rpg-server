package com.rpg.dto.websocket;

public class ActionMessageResponse {

    private String action;
    private MessageResponse body;

    public ActionMessageResponse() {
    }

    public ActionMessageResponse(MessageResponse body) {
        this.action = "message";
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
