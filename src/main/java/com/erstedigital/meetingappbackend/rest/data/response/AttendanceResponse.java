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
    private String participation;
    private Integer meetingId;
    private Integer userId;

    public AttendanceResponse(Attendance attendance) {
        this.id = attendance.getId();
        this.feedbackRating = attendance.getFeedbackRating();
        this.feedbackComment = attendance.getFeedbackComment();
        this.participation = attendance.getParticipation();
        this.meetingId = attendance.getAttendanceMeeting().getId();
        this.userId = attendance.getAttendanceUser().getId();
    }
}
