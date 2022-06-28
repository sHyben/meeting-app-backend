package com.erstedigital.meetingappbackend.websockets.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AttendanceMesssage {
    private Integer id;
    private Boolean participation;
    private Integer presentTime;
    private Date lastJoinedAt;
}
