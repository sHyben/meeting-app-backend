package com.erstedigital.meetingappbackend.persistence.data;

import java.sql.Time;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String meeting_type;
    private Date start_date;
    private Time start_time;
    private Time duration;
    private Time actual_duration;
    private int meeting_cost;
    private String notes_url;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @OneToMany(mappedBy="agenda_meeting")
    private List<Agenda> agendas = new ArrayList<>();

    @OneToMany(mappedBy="attendee_meeting")
    private List<Attendee> attendees = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity_id;

    private String url;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public String getMeeting_type() {
        return meeting_type;
    }

    public void setMeeting_type(String meeting_type) {
        this.meeting_type = meeting_type;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Time getStart_time() {
        return start_time;
    }

    public void setStart_time(Time start_time) {
        this.start_time = start_time;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public Time getActual_duration() {
        return actual_duration;
    }

    public void setActual_duration(Time actual_duration) {
        this.actual_duration = actual_duration;
    }

    public int getMeeting_cost() {
        return meeting_cost;
    }

    public void setMeeting_cost(int meeting_cost) {
        this.meeting_cost = meeting_cost;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Agenda> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<Agenda> agendas) {
        this.agendas = agendas;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Attendee> attendees) {
        this.attendees = attendees;
    }

    public String getNotes_url() {
        return notes_url;
    }

    public void setNotes_url(String notes_url) {
        this.notes_url = notes_url;
    }

    public Activity getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(Activity activity_id) {
        this.activity_id = activity_id;
    }
}
