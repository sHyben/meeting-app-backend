package com.erstedigital.meetingappbackend.persistence.data;

import com.erstedigital.meetingappbackend.rest.data.request.MeetingRequest;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "exchange_id")
    private String exchangeId;
    private String subject;
    private String description;
    @Column(name = "meeting_type")
    private String meetingType;
    private Date start;
    @Column(name = "actual_start")
    private Date actualStart;
    private Date end;
    @Column(name = "actual_end")
    private Date actualEnd;
    @Column(name = "meeting_cost")
    private Integer meetingCost;
    @Column(name = "notes_url")
    private String notesUrl;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @OneToMany(mappedBy="agendaMeeting")
    @ToString.Exclude
    private List<Agenda> agendas;

    @OneToMany(mappedBy="attendeeMeeting")
    @ToString.Exclude
    private List<Attendee> attendees;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activityId;

    private String location;
    private Double latitude;
    private Double longitude;
    private String url;

    public Meeting(MeetingRequest request, User user, Activity activity) {
        this.exchangeId = request.getExchangeId();
        this.subject = request.getSubject();
        this.description = request.getDescription();
        this.meetingType = request.getMeetingType();
        this.start = request.getStart();
        this.actualStart = request.getActualStart();
        this.end = request.getEnd();
        this.actualEnd = request.getActualEnd();
        this.meetingCost = request.getMeetingCost();
        this.notesUrl = request.getNotesUrl();
        this.organizer = user;
        this.agendas = new ArrayList<>();
        this.attendees = new ArrayList<>();
        this.activityId = activity;
        this.location = request.getLocation();
        this.latitude = request.getLatitude();
        this.longitude = request.getLongitude();
        this.url = request.getUrl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Meeting meeting = (Meeting) o;
        return id != null && Objects.equals(id, meeting.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
