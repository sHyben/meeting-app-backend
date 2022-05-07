package com.erstedigital.meetingappbackend.rest.data.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttendeeRequest {
    private String email;
    private Integer feedbackRating;
    private String feedbackComment;
    private String participation;
    private Integer meetingId;
    private Integer positionId;
}
