package com.erstedigital.meetingappbackend.rest.data;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Statistics {
    private Integer totalHours;
    private Float positiveFeedback;
    private Float negativeFeedback;
    private Float neutralFeedback;
    private List<StatisticsDay> statisticsDays;

    public Statistics() {
        this.statisticsDays = new ArrayList<>();
    }

    public Statistics(Integer totalHours, Float positiveFeedback, Float negativeFeedback, Float neutralFeedback, List<StatisticsDay> statisticsDays) {
        this.totalHours = totalHours;
        this.positiveFeedback = positiveFeedback;
        this.negativeFeedback = negativeFeedback;
        this.neutralFeedback = neutralFeedback;
        this.statisticsDays = statisticsDays;
    }

}
