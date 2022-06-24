package com.erstedigital.meetingappbackend.websockets.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class MeetingMessage {
    private Date actualStart;
    private Date actualEnd;
}
