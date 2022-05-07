package com.erstedigital.meetingappbackend.rest.data.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MeetingRequest {
    private String exchangeId;
    private String meetingType;
    private Date startDate;
    private Time startTime;
    private Time duration;
    private Time actualDuration;
    private Integer meetingCost;
    private String notesUrl;
    private Integer organizerId;
    private Integer activityId;
    private String url;
}
