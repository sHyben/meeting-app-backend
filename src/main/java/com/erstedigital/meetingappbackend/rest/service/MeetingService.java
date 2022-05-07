package com.erstedigital.meetingappbackend.rest.service;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import com.erstedigital.meetingappbackend.rest.data.request.MeetingRequest;

import java.util.List;

public interface MeetingService {
    List<Meeting> getAll();

    Meeting findById(Integer id) throws NotFoundException;

    Meeting create(MeetingRequest request) throws NotFoundException;

    Meeting update(Integer id, MeetingRequest request) throws NotFoundException;

    void delete(Integer id) throws NotFoundException;
}
