package com.erstedigital.meetingappbackend.rest.data.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MeetingRequest {
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
    private Integer activityId;
    private String location;
    private Double latitude;
    private Double longitude;
    private String url;
}
