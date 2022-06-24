package com.erstedigital.meetingappbackend.rest.data.response;

import com.erstedigital.meetingappbackend.persistence.data.Note;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteResponse {
    Integer id;
    Integer meetingId;
    String from;
    String text;

    public NoteResponse(Note note) {
        this.id = note.getId();
        this.meetingId = note.getMeeting().getId();
        this.from = note.getFrom().getEmail();
        this.text = note.getText();
    }
}
