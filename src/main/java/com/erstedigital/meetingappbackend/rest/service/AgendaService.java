package com.erstedigital.meetingappbackend.rest.service;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Agenda;
import com.erstedigital.meetingappbackend.rest.data.request.AgendaRequest;

import java.util.List;

public interface AgendaService {
    List<Agenda> getAll();

    Agenda findById(Integer id) throws NotFoundException;

    Agenda create(AgendaRequest request) throws NotFoundException;

    Agenda update(Integer id, AgendaRequest request) throws NotFoundException;

    void delete(Integer id) throws NotFoundException;
}
