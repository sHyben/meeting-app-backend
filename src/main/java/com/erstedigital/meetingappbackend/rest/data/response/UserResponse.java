package com.erstedigital.meetingappbackend.rest.data.response;

import com.erstedigital.meetingappbackend.persistence.data.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserResponse {
    private Integer id;
    private String name;
    private String email;
    private Date modifiedAt;
    private Integer positionId;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.modifiedAt = user.getModified_at();
        this.positionId = user.getUser_position().getId();
    }
}