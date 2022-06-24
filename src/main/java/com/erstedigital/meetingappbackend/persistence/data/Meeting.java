package com.erstedigital.meetingappbackend.persistence.data;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.rest.data.request.MeetingRequest;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;
    @Column(name = "actual_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualStart;
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;
    @Column(name = "actual_end")
    @Temporal(TemporalType.TIMESTAMP)
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

    @OneToMany(mappedBy = "attendanceMeeting")
    @ToString.Exclude
    private List<Attendance> attendances;

    @ManyToMany
    @JoinTable(
            name = "meeting_activity",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id"))
    @ToString.Exclude
    private Set<Activity> activities;

    @OneToOne
    @JoinColumn(name = "running_activity_id")
    private Activity runningActivity;

    private String location;
    private Double latitude;
    private Double longitude;
    private String url;
    private String apolloCode;

    public Meeting(MeetingRequest request, User user, Set<Activity> activities) {
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
        this.attendances = new ArrayList<>();
        this.activities = activities;
        this.location = request.getLocation();
        this.latitude = request.getLatitude();
        this.longitude = request.getLongitude();
        this.url = request.getUrl();
        this.apolloCode = request.getApolloCode();
    }

    public void addActivity(Activity activity) throws NotFoundException {
        if(activity != null) {
            this.activities.add(activity);
        } else {
            throw new NotFoundException();
        }
    }

    public void removeActivity(Activity activity) {
        if(activity != null) {
            this.activities.remove(activity);
        }
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
