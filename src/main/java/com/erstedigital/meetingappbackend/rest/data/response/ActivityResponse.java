package com.erstedigital.meetingappbackend.rest.data.response;

import com.erstedigital.meetingappbackend.persistence.data.Activity;
import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ActivityResponse {
    private Integer id;
    private String type;
    private String title;
    private String text;
    private String answer;
    private String imgUrl;
    private List<Integer> meetings;

    public ActivityResponse(Activity activity) {
        this.id = activity.getId();
        this.type = activity.getType();
        this.title = activity.getTitle();
        this.text = activity.getText();
        this.answer = activity.getAnswer();
        this.imgUrl = activity.getImgUrl();
        this.meetings = activity.getMeetings().stream().map(Meeting::getId).collect(Collectors.toList());;
    }
}
