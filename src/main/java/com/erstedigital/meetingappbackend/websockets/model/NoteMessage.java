package com.erstedigital.meetingappbackend.websockets.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoteMessage {
    private String from;
    private String text;
}
