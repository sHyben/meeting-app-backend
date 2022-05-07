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
    private Integer hourly_cost;

    @OneToMany(mappedBy="attendee_position")
    @ToString.Exclude
    private List<Attendee> position_attendees;

    @OneToMany(mappedBy="user_position")
    @ToString.Exclude
    private List<User> position_users;

    public Position(PositionRequest request) {
        this.name = request.getName();
        this.hourly_cost = request.getHourlyCost();
        this.position_attendees = new ArrayList<>();
        this.position_users = new ArrayList<>();
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

