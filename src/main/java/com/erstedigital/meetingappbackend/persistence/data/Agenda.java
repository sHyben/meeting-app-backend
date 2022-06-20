package com.erstedigital.meetingappbackend.persistence.data;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "agendas")
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToMany(mappedBy = "agenda", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<AgendaPoint> agendaPoints;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting agendaMeeting;

    public Agenda(Meeting meeting) {
        agendaPoints = new ArrayList<>();
        this.agendaMeeting = meeting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Agenda agenda = (Agenda) o;
        return id != null && Objects.equals(id, agenda.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
