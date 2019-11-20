package com.rpg.dto.application;

public class NoteDto {
    private String name;
    private String content;

    public NoteDto(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public NoteDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
