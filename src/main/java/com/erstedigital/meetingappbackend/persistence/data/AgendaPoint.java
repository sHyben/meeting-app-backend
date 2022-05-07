package com.erstedigital.meetingappbackend.persistence.data;

import com.erstedigital.meetingappbackend.rest.data.request.AgendaPointRequest;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "agenda_points")
public class AgendaPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private Integer number;
    private String title;
    private String description;
    private Time duration;
    private String status;

    @ManyToOne
    @JoinColumn(name = "agenda_id")
    private Agenda agenda_id;

    public AgendaPoint(AgendaPointRequest request, Agenda agenda) {
        this.number = request.getNumber();
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.duration = request.getDuration();
        this.status = request.getStatus();
        this.agenda_id = agenda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AgendaPoint that = (AgendaPoint) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
