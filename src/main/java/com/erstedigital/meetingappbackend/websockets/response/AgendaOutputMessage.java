package com.erstedigital.meetingappbackend.websockets.response;

import com.erstedigital.meetingappbackend.persistence.AgendaPointState;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AgendaOutputMessage {
    private Integer agendaPointId;
    private AgendaPointState state;
    private Date actualStart;
    private Date actualEnd;

    private Date anticipatedStart;
    private Date anticipatedEnd;

    public AgendaOutputMessage(Integer agendaPointId, AgendaPointState state, Date actualStart, Date actualEnd, Date anticipatedStart, Date anticipatedEnd) {
        this.agendaPointId = agendaPointId;
        this.state = state;
        this.actualStart = actualStart;
        this.actualEnd = actualEnd;
        this.anticipatedStart = anticipatedStart;
        this.anticipatedEnd = anticipatedEnd;
    }
}
