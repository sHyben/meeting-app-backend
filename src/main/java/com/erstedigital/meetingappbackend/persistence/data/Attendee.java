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
    private Integer feedback_rating;
    private String feedback_comment;
    private String participation;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting attendee_meeting;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position attendee_position;

    public Attendee(AttendeeRequest request, Meeting meeting, Position position) {
        this.email = request.getEmail();
        this.feedback_rating = request.getFeedbackRating();
        this.feedback_comment = request.getFeedbackComment();
        this.participation = request.getParticipation();
        this.attendee_meeting = meeting;
        this.attendee_position = position;
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
