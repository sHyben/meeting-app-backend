package com.erstedigital.meetingappbackend.websockets.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityOutputMessage {

    private Integer activityId;

    public ActivityOutputMessage(Integer activityId) {
        this.activityId = activityId;
    }
}
