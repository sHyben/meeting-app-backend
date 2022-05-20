package com.erstedigital.meetingappbackend.persistence.data;

import com.erstedigital.meetingappbackend.rest.data.request.AttendeeRequest;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "attendees")
public class Attendee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String email;
    @Column(name = "feedback_rating")
    private Integer feedbackRating;
    @Column(name = "feedback_comment")
    private String feedbackComment;
    private String participation;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting attendeeMeeting;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position attendeePosition;

    public Attendee(AttendeeRequest request, Meeting meeting, Position position) {
        this.email = request.getEmail();
        this.feedbackRating = request.getFeedbackRating();
        this.feedbackComment = request.getFeedbackComment();
        this.participation = request.getParticipation();
        this.attendeeMeeting = meeting;
        this.attendeePosition = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Attendee attendee = (Attendee) o;
        return id != null && Objects.equals(id, attendee.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
