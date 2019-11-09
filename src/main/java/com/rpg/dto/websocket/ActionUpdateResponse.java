package com.rpg.dto.websocket;

public class ActionUpdateResponse {

    private String action;
    private String target;

    public ActionUpdateResponse() {
    }

    public ActionUpdateResponse(String action, String target) {
        this.action = action;
        this.target = target;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
