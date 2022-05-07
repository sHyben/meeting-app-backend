package com.erstedigital.meetingappbackend.rest.service;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Position;
import com.erstedigital.meetingappbackend.rest.data.request.PositionRequest;

import java.util.List;

public interface PositionService {
    List<Position> getAll();

    Position findById(Integer id) throws NotFoundException;

    Position create(PositionRequest request);

    Position update(Integer id, PositionRequest request) throws NotFoundException;

    void delete(Integer id) throws NotFoundException;
}
