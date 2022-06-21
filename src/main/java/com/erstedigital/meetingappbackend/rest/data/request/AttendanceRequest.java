package com.erstedigital.meetingappbackend.rest.data.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttendanceRequest {
    private Integer feedbackRating;
    private String feedbackComment;
    private boolean participation;
    private Integer meetingId;
    private Integer userId;
}
