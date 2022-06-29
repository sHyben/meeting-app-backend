package com.erstedigital.meetingappbackend.rest.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StatisticsDay {
    private LocalDate day;
    private Integer invited;
    private Integer attended;
    private Integer time;

    public StatisticsDay(LocalDate day, Integer invited, Integer attended, Integer time) {
        this.day = day;
        this.invited = invited;
        this.attended = attended;
        this.time = time;
    }
}
