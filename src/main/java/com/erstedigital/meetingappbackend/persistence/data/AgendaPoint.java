package com.erstedigital.meetingappbackend.persistence.data;

import com.erstedigital.meetingappbackend.rest.data.request.AgendaPointRequest;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "agenda_points")
public class AgendaPoint implements Comparable<AgendaPoint> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private Integer number;
    private String title;
    private String description;
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date start;
    @Column(name = "actual_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualStart;
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "actual_end")
    private Date actualEnd;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;

    public AgendaPoint(AgendaPointRequest request, Agenda agenda) {
        this.number = request.getNumber();
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.start = request.getStart();
        this.actualStart = request.getActualStart();
        this.end = request.getEnd();
        this.actualEnd = request.getActualEnd();
        this.status = request.getStatus();
        this.agenda = agenda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AgendaPoint that = (AgendaPoint) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int compareTo(AgendaPoint o) {
        return getStart().compareTo(o.getStart());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
