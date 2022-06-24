package com.erstedigital.meetingappbackend.rest.data.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class AgendaPointRequest {
    private Integer number;
    private String title;
    private String description;
    private Date start;
    private Date end;
    private Date actualStart;
    private Date actualEnd;
    private String status;
    private Integer agendaId;
}
