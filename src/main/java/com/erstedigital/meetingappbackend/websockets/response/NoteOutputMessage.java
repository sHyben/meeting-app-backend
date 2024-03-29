package com.erstedigital.meetingappbackend.websockets.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteOutputMessage {
    private Integer id;
    private String from;
    private String text;
    private String time;

    public NoteOutputMessage(Integer id, String from, String text, String time) {
        this.id = id;
        this.from = from;
        this.text = text;
        this.time = time;
    }
}
