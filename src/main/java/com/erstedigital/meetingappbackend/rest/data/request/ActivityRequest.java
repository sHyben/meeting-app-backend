package com.erstedigital.meetingappbackend.rest.data.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActivityRequest {
    private String type;
    private String title;
    private String text;
    private String answer;
    private String imgUrl;
}
