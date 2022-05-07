package com.erstedigital.meetingappbackend.rest.data.response;

import com.erstedigital.meetingappbackend.persistence.data.AgendaPoint;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class AgendaPointResponse {
    private Integer id;
    private Integer number;
    private String title;
    private String description;
    private Time duration;
    private String status;
    private Integer agendaId;

    public AgendaPointResponse(AgendaPoint agendaPoint) {
        this.id = agendaPoint.getId();
        this.number = agendaPoint.getNumber();
        this.title = agendaPoint.getTitle();
        this.description = agendaPoint.getDescription();
        this.duration = agendaPoint.getDuration();
        this.status = agendaPoint.getStatus();
        this.agendaId = agendaPoint.getAgenda_id().getId();
    }
}
