package com.erstedigital.meetingappbackend.persistence.data;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "agenda_points")
public class AgendaPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    private int number;
    private String title;
    private String description;
    private Time duration;
    private String status;

    @ManyToOne
    @JoinColumn(name = "agenda_id")
    private Agenda agenda_points_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAgenda_points_id(Agenda agenda_points_id) {
        this.agenda_points_id = agenda_points_id;
    }
}
