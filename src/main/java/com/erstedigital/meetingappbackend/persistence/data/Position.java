package com.erstedigital.meetingappbackend.persistence.data;

import com.erstedigital.meetingappbackend.rest.data.request.PositionRequest;
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
@Table(name = "positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    @Column(name = "hourly_cost")
    private Integer hourlyCost;

    @OneToMany(mappedBy="attendeePosition")
    @ToString.Exclude
    private List<Attendee> positionAttendees;

    @OneToMany(mappedBy="userPosition")
    @ToString.Exclude
    private List<User> positionUsers;

    public Position(PositionRequest request) {
        this.name = request.getName();
        this.hourlyCost = request.getHourlyCost();
        this.positionAttendees = new ArrayList<>();
        this.positionUsers = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Position position = (Position) o;
        return id != null && Objects.equals(id, position.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

