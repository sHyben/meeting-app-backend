package com.erstedigital.meetingappbackend.rest.data.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    private String name;
    private String email;
    private Integer positionId;
}
