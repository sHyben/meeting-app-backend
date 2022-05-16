package com.erstedigital.meetingappbackend.rest.data.response;

import com.erstedigital.meetingappbackend.persistence.data.Agenda;
import com.erstedigital.meetingappbackend.persistence.data.Attendee;
import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MeetingResponse {
    private Integer id;
    private String exchangeId;
    private String meetingType;
    private Date startDate;
    private Time startTime;
    private Time duration;
    private Time actualDuration;
    private Integer meetingCost;
    private String notesUrl;
    private Integer organizerId;
    private List<Integer> agendas;
    private List<Integer> attendees;
    private Integer activityId;
    private String url;

    public MeetingResponse(Meeting meeting) {
        this.id = meeting.getId();
        this.exchangeId = meeting.getExchangeId();
        this.meetingType = meeting.getMeeting_type();
        this.startDate = meeting.getStart_date();
        this.startTime = meeting.getStart_time();
        this.duration = meeting.getDuration();
        this.actualDuration = meeting.getActual_duration();
        this.meetingCost = meeting.getMeeting_cost();
        this.notesUrl = meeting.getNotes_url();
        this.organizerId = meeting.getOrganizer().getId();
        this.agendas = convertAgendaListToIdList(meeting);
        this.attendees = convertAttendeeListToIdList(meeting);
        this.activityId = meeting.getActivity_id().getId();
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
