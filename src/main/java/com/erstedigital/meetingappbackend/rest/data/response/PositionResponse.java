package com.erstedigital.meetingappbackend.rest.data.response;

import com.erstedigital.meetingappbackend.persistence.data.Attendee;
import com.erstedigital.meetingappbackend.persistence.data.Position;
import com.erstedigital.meetingappbackend.persistence.data.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PositionResponse {
    private Integer id;
    private String name;
    private Integer hourlyCost;
    private List<Integer> attendees;
    private List<Integer> users;

    public PositionResponse(Position position) {
        this.id = position.getId();
        this.name = position.getName();
        this.hourlyCost = position.getHourlyCost();
        this.attendees = position.getPositionAttendees().stream().map(Attendee::getId).collect(Collectors.toList());
        this.users = position.getPositionUsers().stream().map(User::getId).collect(Collectors.toList());;
    }
}
