package com.erstedigital.meetingappbackend.websockets.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MeetingOutputMessage {
    private Integer id;
    private Date actualStart;
    private Date actualEnd;
    private Date anticipatedEnd;

    public MeetingOutputMessage(Integer id, Date actualStart, Date actualEnd, Date anticipatedEnd) {
        this.id = id;
        this.actualStart = actualStart;
        this.actualEnd = actualEnd;
        this.anticipatedEnd = anticipatedEnd;
    }

}
