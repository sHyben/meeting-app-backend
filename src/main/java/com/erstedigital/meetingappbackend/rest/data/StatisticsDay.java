package com.erstedigital.meetingappbackend.rest.data;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class StatisticsDay {
    private Date day;
    private Integer invited;
    private Integer attended;
    private Integer time;

    public StatisticsDay(Date day, Integer invited, Integer attended, Integer time) {
        this.day = day;
        this.invited = invited;
        this.attended = attended;
        this.time = time;
    }
}
