package com.erstedigital.meetingappbackend.persistence.data;

import javax.persistence.*;

@Entity
@Table(name = "attendees")
public class Attendee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String email;
    private int feedback_rating;
    private String feedback_comment;
    private String participation;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting attendee_meeting;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position attendee_position;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFeedback_rating() {
        return feedback_rating;
    }

    public void setFeedback_rating(int feedback_rating) {
        this.feedback_rating = feedback_rating;
    }

    public String getFeedback_comment() {
        return feedback_comment;
    }

    public void setFeedback_comment(String feedback_comment) {
        this.feedback_comment = feedback_comment;
    }

    public String getParticipation() {
        return participation;
    }

    public void setParticipation(String participation) {
        this.participation = participation;
    }

    public void setAttendee_meeting(Meeting attendee_meeting) {
        this.attendee_meeting = attendee_meeting;
    }

    public Position getAttendee_position() {
        return attendee_position;
    }

    public void setAttendee_position(Position attendee_position) {
        this.attendee_position = attendee_position;
    }
}
