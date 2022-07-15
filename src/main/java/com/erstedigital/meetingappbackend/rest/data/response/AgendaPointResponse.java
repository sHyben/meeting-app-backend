package com.erstedigital.meetingappbackend.rest.data.response;

import com.erstedigital.meetingappbackend.persistence.data.AgendaPoint;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class AgendaPointResponse {
    private Integer id;
    private Integer number;
    private String title;
    private String description;
    private Date start;
    private Date end;
    private Date actualStart;
    private Date anticipatedStart;
    private Date actualEnd;
    private Date anticipateEnd;
    private String status;
    private Integer agendaId;

    public AgendaPointResponse(AgendaPoint agendaPoint) {
        this.id = agendaPoint.getId();
        this.number = agendaPoint.getNumber();
        this.title = agendaPoint.getTitle();
        this.description = agendaPoint.getDescription();
        this.start = agendaPoint.getStart();
        this.end = agendaPoint.getEnd();
        this.actualStart = agendaPoint.getActualStart();
        this.actualEnd = agendaPoint.getActualEnd();
        this.anticipatedStart = agendaPoint.getAnticipatedStart();
        this.anticipateEnd = agendaPoint.getAnticipatedEnd();
        this.status = agendaPoint.getStatus();
        this.agendaId = agendaPoint.getAgenda().getId();
    }
}
