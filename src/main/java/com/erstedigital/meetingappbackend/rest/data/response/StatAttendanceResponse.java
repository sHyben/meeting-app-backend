package com.erstedigital.meetingappbackend.rest.data.response;

import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class StatAttendanceResponse {
    private List<Integer> meetings;

    public StatAttendanceResponse(List<Meeting> meetings) {
        this.meetings = meetings.stream().map(Meeting::getId).collect(Collectors.toList());
    }
}
