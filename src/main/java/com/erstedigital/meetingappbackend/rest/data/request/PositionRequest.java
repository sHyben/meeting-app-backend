package com.erstedigital.meetingappbackend.rest.data.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PositionRequest {
    private String name;
    private Integer hourlyCost;
}
