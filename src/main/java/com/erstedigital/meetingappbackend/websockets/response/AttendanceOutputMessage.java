package com.erstedigital.meetingappbackend.websockets.response;

import com.erstedigital.meetingappbackend.persistence.data.Attendance;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AttendanceOutputMessage {
    private Integer id;
    private Boolean participation;
    private Integer presentTime;
    private Date lastJoinedAt;

    public AttendanceOutputMessage(Attendance attendance) {
        this.id = attendance.getId();
        this.participation = attendance.isParticipation();
        this.presentTime = attendance.getPresenceTime();
        this.lastJoinedAt = attendance.getLastJoinedAt();
    }
}
