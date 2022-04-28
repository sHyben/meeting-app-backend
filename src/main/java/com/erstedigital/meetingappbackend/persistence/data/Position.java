package com.erstedigital.meetingappbackend.persistence.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

@Table(name = "positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    private int hourly_cost;

    @OneToMany(mappedBy="attendee_position")
    private List<Attendee> position_attendees = new ArrayList<>();

    @OneToMany(mappedBy="user_position")
    private List<User> position_users = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHourly_cost() {
        return hourly_cost;
    }

    public void setHourly_cost(int hourly_cost) {
        this.hourly_cost = hourly_cost;
    }
}

