package com.erstedigital.meetingappbackend.rest.data.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
public class AgendaPointRequest {
    private Integer number;
    private String title;
    private String description;
    private Time duration;
    private String status;
    private Integer agenda_id;
}
