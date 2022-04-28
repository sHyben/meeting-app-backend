package com.erstedigital.meetingappbackend.persistence.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agendas")
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToMany(mappedBy="agenda_points_id")
    private List<AgendaPoint> agenda_points = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting agenda_meeting;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<AgendaPoint> getAgenda_points() {
        return agenda_points;
    }

    public void setAgenda_points(List<AgendaPoint> agenda_points) {
        this.agenda_points = agenda_points;
    }

    public void setAgenda_meeting(Meeting agenda_meeting) {
        this.agenda_meeting = agenda_meeting;
    }
}
