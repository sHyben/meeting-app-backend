package com.erstedigital.meetingappbackend.rest.data.response;

import com.erstedigital.meetingappbackend.persistence.data.Agenda;
import com.erstedigital.meetingappbackend.persistence.data.AgendaPoint;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AgendaResponse {
    private Integer id;
    private List<AgendaPointResponse> meetingPoints;
    private Integer meetingId;

    public AgendaResponse(Agenda agenda) {
        this.id = agenda.getId();
        this.meetingPoints = agenda.getAgendaPoints().stream().map(AgendaPointResponse::new).collect(Collectors.toList());
        this.meetingId = agenda.getAgendaMeeting().getId();
    }
}
