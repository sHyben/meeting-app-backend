package com.erstedigital.meetingappbackend.rest.data.response;

import com.erstedigital.meetingappbackend.persistence.data.Activity;
import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ActivityResponse {
    private Integer id;
    private String type;
    private String title;
    private String text;
    private String answer;
    private String img_url;
    private List<Integer> meetings;

    public ActivityResponse(Activity activity) {
        this.id = activity.getId();
        this.type = activity.getType();
        this.title = activity.getTitle();
        this.text = activity.getText();
        this.answer = activity.getAnswer();
        this.img_url = activity.getImg_url();
        this.meetings = convertEntityListToIdList(activity);
    }

    private List<Integer> convertEntityListToIdList(Activity activity) {
        List<Integer> idList = new ArrayList<>();
        for (Meeting meeting : activity.getMeetings()) {
            idList.add(meeting.getId());
        }
        return idList;
    }
}
