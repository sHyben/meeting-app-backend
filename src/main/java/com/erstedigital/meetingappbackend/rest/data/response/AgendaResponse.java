package com.erstedigital.meetingappbackend.rest.data.response;

import com.erstedigital.meetingappbackend.persistence.data.Agenda;
import com.erstedigital.meetingappbackend.persistence.data.AgendaPoint;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AgendaResponse {
    private Integer id;
    private List<Integer> meetingPoints;
    private Integer meetingId;

    public AgendaResponse(Agenda agenda) {
        this.id = agenda.getId();
        this.meetingPoints = convertEntityListToIdList(agenda);
        this.meetingId = agenda.getAgenda_meeting().getId();
    }

    private List<Integer> convertEntityListToIdList(Agenda agenda) {
        List<Integer> idList = new ArrayList<>();
        for (AgendaPoint agendaPoint : agenda.getAgenda_points()) {
            idList.add(agendaPoint.getId());
        }
        return idList;
    }
}
