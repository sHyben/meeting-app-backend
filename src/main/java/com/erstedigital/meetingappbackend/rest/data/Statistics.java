package com.erstedigital.meetingappbackend.rest.data;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Statistics {
    private Integer totalHours;
    private Integer totalMeetings;
    private Integer totalAttendees;
    private Integer positiveFeedback;
    private Integer negativeFeedback;
    private Integer neutralFeedback;
    private Integer onTime;
    private Integer overTime;
    private Integer underTime;

    private List<StatisticsDay> statisticsDays;

    public Statistics() {
        this.statisticsDays = new ArrayList<>();
    }
}
