package com.erstedigital.meetingappbackend.websockets.model;

import com.erstedigital.meetingappbackend.persistence.AgendaPointState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaMessage {
    private Integer agendaPointId;
    private AgendaPointState state;
}
