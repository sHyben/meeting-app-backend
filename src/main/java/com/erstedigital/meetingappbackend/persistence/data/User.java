package com.erstedigital.meetingappbackend.persistence.data;

import com.erstedigital.meetingappbackend.rest.data.request.UserRequest;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    private String email;
    @Column(name = "modified_at")
    private Date modifiedAt;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position userPosition;

    @ManyToMany(mappedBy = "attendees")
    private Set<Meeting> meetings;

    public User(UserRequest request, Position position) {
        this.name = request.getName();
        this.email = request.getEmail();
        this.modifiedAt = new Date(Calendar.getInstance().getTime().getTime());
        this.userPosition = position;
        this.meetings = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
