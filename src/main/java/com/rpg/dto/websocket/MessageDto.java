package com.rpg.dto.websocket;

public class MessageDto {

    private String content;
    private String accessToken;
    private String characterName;

    public MessageDto(String content, String accessToken, String characterName) {
        this.content = content;
        this.accessToken = accessToken;
        this.characterName = characterName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}
