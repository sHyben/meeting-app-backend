package com.erstedigital.meetingappbackend.rest.data.response;

import com.erstedigital.meetingappbackend.persistence.data.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserResponse {
    private Integer id;
    private String name;
    private String email;
    private Date modifiedAt;
    private Integer positionId;
    private AttendanceResponse attendance;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.modifiedAt = user.getModifiedAt();
        this.positionId = user.getUserPosition().getId();
    }

    public UserResponse(User user, Integer meetingId) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.modifiedAt = user.getModifiedAt();
        this.positionId = user.getUserPosition().getId();

        user.getAttendances().stream().filter(attendance -> attendance.getAttendanceMeeting().getId().equals(meetingId))
                .findAny().ifPresent(meetingAttendance -> this.attendance = new AttendanceResponse(meetingAttendance));

    }
}
