package com.erstedigital.meetingappbackend.websockets.model;

import com.erstedigital.meetingappbackend.persistence.AgendaPointState;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class AgendaMessage {
    private Integer agendaPointId;
    private AgendaPointState state;
    private Date actualStart;
    private Date actualEnd;
    private Date anticipatedStart;
    private Date anticipatedEnd;
}
