package com.erstedigital.meetingappbackend.websockets.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NoteOutputMessage {
    private Integer id;
    private String from;
    private String text;
    private Date createdAt;

    public NoteOutputMessage(Integer id, String from, String text, Date createdAt) {
        this.id = id;
        this.from = from;
        this.text = text;
        this.createdAt = createdAt;
    }
}
