package com.erstedigital.meetingappbackend.rest.service;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.AgendaPoint;
import com.erstedigital.meetingappbackend.rest.data.request.AgendaPointRequest;

import java.util.List;

public interface AgendaPointService {
    List<AgendaPoint> getAll();

    AgendaPoint findById(Integer id) throws NotFoundException;

    AgendaPoint create(AgendaPointRequest request) throws NotFoundException;

    AgendaPoint update(Integer id, AgendaPointRequest request) throws NotFoundException;

    void delete(Integer id) throws NotFoundException;
}
