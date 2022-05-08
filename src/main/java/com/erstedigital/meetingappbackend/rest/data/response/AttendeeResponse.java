package com.erstedigital.meetingappbackend.rest.data.response;

import com.erstedigital.meetingappbackend.persistence.data.Attendee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendeeResponse {
    private Integer id;
    private String email;
    private Integer feedbackRating;
    private String feedbackComment;
    private String participation;
    private Integer meetingId;
    private Integer positionId;

    public AttendeeResponse(Attendee attendee) {
        this.id = attendee.getId();
        this.email = attendee.getEmail();
        this.feedbackRating = attendee.getFeedback_rating();
        this.feedbackComment = attendee.getFeedback_comment();
        this.participation = attendee.getParticipation();
        this.meetingId = attendee.getAttendee_meeting().getId();
        this.positionId = attendee.getAttendee_position().getId();
    }
}