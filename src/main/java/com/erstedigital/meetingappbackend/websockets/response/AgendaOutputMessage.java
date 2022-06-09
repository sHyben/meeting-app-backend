package com.erstedigital.meetingappbackend.websockets.response;

import com.erstedigital.meetingappbackend.persistence.AgendaPointState;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class AgendaOutputMessage {
    private Integer agendaPointId;
    private AgendaPointState state;
    private Date actualStart;
    private Date actualEnd;

    public AgendaOutputMessage(Integer agendaPointId, AgendaPointState state, Date actualStart, Date actualEnd) {
        this.agendaPointId = agendaPointId;
        this.state = state;
        this.actualStart = actualStart;
        this.actualEnd = actualEnd;
    }
}
