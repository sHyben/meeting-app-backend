package com.erstedigital.meetingappbackend.rest.service;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Activity;
import com.erstedigital.meetingappbackend.rest.data.request.ActivityRequest;

import java.util.List;
import java.util.Set;

public interface ActivityService {
    List<Activity> getAll();

    Activity findById(Integer id) throws NotFoundException;

    Set<Activity> findById(Set<Integer> id);

    Activity create(ActivityRequest request);

    Activity update(Integer id, ActivityRequest request) throws NotFoundException;

    void delete(Integer id) throws NotFoundException;
}
