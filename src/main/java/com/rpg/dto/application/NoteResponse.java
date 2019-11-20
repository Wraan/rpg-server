package com.rpg.dto.application;

public class NoteResponse {
    private long id;
    private String name;
    private String content;

    public NoteResponse(long id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public NoteResponse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
