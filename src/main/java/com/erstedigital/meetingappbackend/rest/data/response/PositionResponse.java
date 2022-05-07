package com.erstedigital.meetingappbackend.rest.data.response;

import com.erstedigital.meetingappbackend.persistence.data.Attendee;
import com.erstedigital.meetingappbackend.persistence.data.Position;
import com.erstedigital.meetingappbackend.persistence.data.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
        this.hourlyCost = position.getHourly_cost();
        this.attendees = convertAttendeeListToIdList(position);
        this.users = convertUserListToIdList(position);
    }

    private List<Integer> convertUserListToIdList(Position position) {
        List<Integer> idList = new ArrayList<>();
        for (User user : position.getPosition_users()) {
            idList.add(user.getId());
        }
        return idList;
    }

    private List<Integer> convertAttendeeListToIdList(Position position) {
        List<Integer> idList = new ArrayList<>();
        for (Attendee attendee : position.getPosition_attendees()) {
            idList.add(attendee.getId());
        }
        return idList;
    }
}
