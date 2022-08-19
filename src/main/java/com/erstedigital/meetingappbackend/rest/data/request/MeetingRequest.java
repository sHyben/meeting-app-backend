package com.erstedigital.meetingappbackend.rest.data.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class MeetingRequest {
    private String exchangeId;
    private String subject;
    private String description;
    private String meetingType;
    private String feedbackType;
    private String feedbackUrl;
    private String apolloCode;
    private Date start;
    private Date actualStart;
    private Date end;
    private Date actualEnd;
    private Date anticipatedEnd;
    private Integer meetingCost;
    private String notesUrl;
    private Integer organizerId;
    private Set<Integer> activities;
    private String location;
    private Double latitude;
    private Double longitude;
    private String url;
    private Set<Integer> attendees;
}
