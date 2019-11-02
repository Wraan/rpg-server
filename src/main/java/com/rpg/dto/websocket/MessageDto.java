package com.rpg.dto.websocket;

public class MessageDto {

    private String content;
    private String characterName;

    public MessageDto() {
    }

    public MessageDto(String content, String characterName) {
        this.content = content;
        this.characterName = characterName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}
