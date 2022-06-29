package com.erstedigital.meetingappbackend.rest.data.response;
import com.erstedigital.meetingappbackend.persistence.data.*;
import com.erstedigital.meetingappbackend.persistence.data.Agenda;
import com.erstedigital.meetingappbackend.persistence.data.Attendance;
import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MeetingResponse {
    private Integer id;
    private String exchangeId;
    private String subject;
    private String description;
    private String meetingType;
    private String feedbackType;
    private String apolloCode;
    private Date start;
    private Date actualStart;
    private Date end;
    private Date actualEnd;
    private Integer meetingCost;
    private String notesUrl;
    private Integer organizerId;
    private List<Integer> agendas;
    private List<Integer> activities;
    private List<Integer> attendances;
    private String location;
    private Double latitude;
    private Double longitude;
    private String url;
    private Integer runningActivity;

    public MeetingResponse(Meeting meeting) {
        this.id = meeting.getId();
        this.exchangeId = meeting.getExchangeId();
        this.subject = meeting.getSubject();
        this.description = meeting.getDescription();
        this.meetingType = meeting.getMeetingType();
        this.feedbackType = meeting.getFeedbackType();
        this.start = meeting.getStart();
        this.actualStart = meeting.getActualStart();
        this.end = meeting.getEnd();
        this.actualEnd = meeting.getActualEnd();
        this.meetingCost = meeting.getMeetingCost();
        this.notesUrl = meeting.getNotesUrl();
        this.organizerId = meeting.getOrganizer().getId();
        this.agendas = meeting.getAgendas().stream().map(Agenda::getId).collect(Collectors.toList());
        this.activities = meeting.getActivities().stream().map(Activity::getId).collect(Collectors.toList());
        this.attendances = meeting.getAttendances().stream().map(Attendance::getId).collect(Collectors.toList());
        this.location = meeting.getLocation();
        this.latitude = meeting.getLatitude();
        this.longitude = meeting.getLongitude();
        this.url = meeting.getUrl();

        if (meeting.getRunningActivity() != null) {
            this.runningActivity = meeting.getRunningActivity().getId();
        } else {
            this.runningActivity = -1;
        }

        this.apolloCode = meeting.getApolloCode();
    }
}
