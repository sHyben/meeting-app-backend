package com.erstedigital.meetingappbackend.rest.service.impl;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Agenda;
import com.erstedigital.meetingappbackend.persistence.repository.AgendaRepository;
import com.erstedigital.meetingappbackend.rest.data.request.AgendaRequest;
import com.erstedigital.meetingappbackend.rest.service.AgendaService;
import com.erstedigital.meetingappbackend.rest.service.MeetingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendaServiceImpl implements AgendaService {
    private final AgendaRepository agendaRepository;
    private final MeetingService meetingService;

    public AgendaServiceImpl(AgendaRepository agendaRepository, MeetingService meetingService) {
        this.agendaRepository = agendaRepository;
        this.meetingService = meetingService;
    }

    @Override
    public List<Agenda> getAll() {
        return agendaRepository.findAll();
    }

    @Override
    public Agenda findById(Integer id) throws NotFoundException {
        Optional<Agenda> agenda = agendaRepository.findById(id);
        if(agenda.isPresent()) {
            return agenda.get();
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public Agenda create(AgendaRequest request) throws NotFoundException {
        return agendaRepository.save(new Agenda(meetingService.findById(request.getMeetingId())));
    }

    @Override
    public Agenda update(Integer id, AgendaRequest request) throws NotFoundException {
        Agenda agenda = findById(id);
        if(request.getMeetingId() != null) {
            agenda.setAgendaMeeting(meetingService.findById(request.getMeetingId()));
        }
        return agendaRepository.save(agenda);
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        agendaRepository.save(findById(id));
    }
}
