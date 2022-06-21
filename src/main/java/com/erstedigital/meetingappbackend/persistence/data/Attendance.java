package com.erstedigital.meetingappbackend.persistence.data;

import com.erstedigital.meetingappbackend.rest.data.request.AttendanceRequest;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "attendances")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "feedback_rating")
    private Integer feedbackRating;
    @Column(name = "feedback_comment")
    private String feedbackComment;
    private boolean participation;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting attendanceMeeting;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User attendanceUser;

    @Column(name = "presence_time")
    private Integer presenceTime;

    public Attendance(AttendanceRequest request, Meeting meeting, User user) {
        this.feedbackRating = request.getFeedbackRating();
        this.feedbackComment = request.getFeedbackComment();
        this.participation = request.isParticipation();
        this.attendanceMeeting = meeting;
        this.attendanceUser = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Attendance attendance = (Attendance) o;
        return id != null && Objects.equals(id, attendance.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
