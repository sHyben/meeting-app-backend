package com.erstedigital.meetingappbackend.rest.service;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.User;
import com.erstedigital.meetingappbackend.rest.data.request.UserRequest;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User findById(Integer id) throws NotFoundException;

    User create(UserRequest request) throws NotFoundException;

    List<User> create(List<UserRequest> request) throws NotFoundException;

    User update(Integer id, UserRequest request) throws NotFoundException;

    void delete(Integer id) throws NotFoundException;

    List<User> getMeetingAttendees(Integer id);
}
