package com.erstedigital.meetingappbackend.websockets.response;

import com.erstedigital.meetingappbackend.persistence.AgendaPointState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaOutputMessage {
    private Integer agendaPointId;
    private AgendaPointState state;
    private String time;

    public AgendaOutputMessage(Integer agendaPointId, AgendaPointState state, String time) {
        this.agendaPointId = agendaPointId;
        this.state = state;
        this.time = time;
    }
}
