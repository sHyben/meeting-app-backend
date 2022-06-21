package com.erstedigital.meetingappbackend.rest.data.response;

import com.erstedigital.meetingappbackend.persistence.data.Attendance;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceResponse {
    private Integer id;
    private Integer feedbackRating;
    private String feedbackComment;
    private boolean participation;
    private Integer meetingId;
    private Integer userId;
    private Integer presenceTime;

    public AttendanceResponse(Attendance attendance) {
        this.id = attendance.getId();
        this.feedbackRating = attendance.getFeedbackRating();
        this.feedbackComment = attendance.getFeedbackComment();
        this.participation = attendance.isParticipation();
        this.meetingId = attendance.getAttendanceMeeting().getId();
        this.userId = attendance.getAttendanceUser().getId();
        this.presenceTime = attendance.getPresenceTime();
    }
}
