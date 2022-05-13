package com.erstedigital.meetingappbackend.rest.data.response;

import com.erstedigital.meetingappbackend.persistence.data.Agenda;
import com.erstedigital.meetingappbackend.persistence.data.Attendee;
import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MeetingResponse {
    private Integer id;
    private String exchangeId;
    private String subject;
    private String description;
    private String meetingType;
    private Date start;
    private Date actualStart;
    private Date end;
    private Date actualEnd;
    private Integer meetingCost;
    private String notesUrl;
    private Integer organizerId;
    private List<Integer> agendas;
    private List<Integer> attendees;
    private Integer activityId;
    private String location;
    private Double latitude;
    private Double longitude;
    private String url;

    public MeetingResponse(Meeting meeting) {
        this.id = meeting.getId();
        this.exchangeId = meeting.getExchange_id();
        this.subject = meeting.getSubject();
        this.description = meeting.getDescription();
        this.meetingType = meeting.getMeeting_type();
        this.start = meeting.getStart();
        this.actualStart = meeting.getActual_start();
        this.end = meeting.getEnd();
        this.actualEnd = meeting.getActual_end();
        this.meetingCost = meeting.getMeeting_cost();
        this.notesUrl = meeting.getNotes_url();
        this.organizerId = meeting.getOrganizer().getId();
        this.agendas = convertAgendaListToIdList(meeting);
        this.attendees = convertAttendeeListToIdList(meeting);
        this.activityId = meeting.getActivity_id().getId();
        this.location = meeting.getLocation();
        this.latitude = meeting.getLatitude();
        this.longitude = meeting.getLongitude();
        this.url = meeting.getUrl();
    }

    private List<Integer> convertAgendaListToIdList(Meeting meeting) {
        List<Integer> idList = new ArrayList<>();
        for (Agenda agenda : meeting.getAgendas()) {
            idList.add(agenda.getId());
        }
        return idList;
    }

    private List<Integer> convertAttendeeListToIdList(Meeting meeting) {
        List<Integer> idList = new ArrayList<>();
        for (Attendee attendee : meeting.getAttendees()) {
            idList.add(attendee.getId());
        }
        return idList;
    }
}
