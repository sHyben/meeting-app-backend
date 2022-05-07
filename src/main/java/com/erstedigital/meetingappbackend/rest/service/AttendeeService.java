package com.erstedigital.meetingappbackend.rest.service;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Attendee;
import com.erstedigital.meetingappbackend.rest.data.request.AttendeeRequest;

import java.util.List;

public interface AttendeeService {
    List<Attendee> getAll();

    Attendee findById(Integer id) throws NotFoundException;

    Attendee create(AttendeeRequest request) throws NotFoundException;

    Attendee update(Integer id, AttendeeRequest request) throws NotFoundException;

    void delete(Integer id) throws NotFoundException;
}
