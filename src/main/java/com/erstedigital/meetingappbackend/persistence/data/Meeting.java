package com.erstedigital.meetingappbackend.persistence.data;

import com.erstedigital.meetingappbackend.rest.data.request.MeetingRequest;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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
    private String exchange_id;
    private String subject;
    private String description;
    private String meeting_type;
    private Date start;
    private Date actual_start;
    private Date end;
    private Date actual_end;
    private Integer meeting_cost;
    private String notes_url;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @OneToMany(mappedBy="agenda_meeting")
    @ToString.Exclude
    private List<Agenda> agendas;

    @OneToMany(mappedBy="attendee_meeting")
    @ToString.Exclude
    private List<Attendee> attendees;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity_id;

    private String location;
    private Double latitude;
    private Double longitude;
    private String url;

    public Meeting(MeetingRequest request, User user, Activity activity) {
        this.exchange_id = request.getExchangeId();
        this.subject = request.getSubject();
        this.description = request.getDescription();
        this.meeting_type = request.getMeetingType();
        this.start = request.getStart();
        this.actual_start = request.getActualStart();
        this.end = request.getEnd();
        this.actual_end = request.getActualEnd();
        this.meeting_cost = request.getMeetingCost();
        this.notes_url = request.getNotesUrl();
        this.organizer = user;
        this.agendas = new ArrayList<>();
        this.attendees = new ArrayList<>();
        this.activity_id = activity;
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
