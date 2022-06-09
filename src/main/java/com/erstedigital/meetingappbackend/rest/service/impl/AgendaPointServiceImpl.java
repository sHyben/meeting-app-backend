package com.erstedigital.meetingappbackend.rest.service.impl;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.AgendaPoint;
import com.erstedigital.meetingappbackend.persistence.repository.AgendaPointRepository;
import com.erstedigital.meetingappbackend.rest.data.request.AgendaPointRequest;
import com.erstedigital.meetingappbackend.rest.service.AgendaPointService;
import com.erstedigital.meetingappbackend.rest.service.AgendaService;
import com.erstedigital.meetingappbackend.websockets.model.AgendaMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendaPointServiceImpl implements AgendaPointService {
    private final AgendaPointRepository agendaPointRepository;
    private final AgendaService agendaService;

    public AgendaPointServiceImpl(AgendaPointRepository agendaPointRepository, AgendaService agendaService) {
        this.agendaPointRepository = agendaPointRepository;
        this.agendaService = agendaService;
    }

    @Override
    public List<AgendaPoint> getAll() {
        return agendaPointRepository.findAll();
    }

    @Override
    public List<AgendaPoint> getAll(Integer agendaId) {
        return agendaPointRepository.findAll();
    }

    @Override
    public AgendaPoint findById(Integer id) throws NotFoundException {
        Optional<AgendaPoint> agendaPoint = agendaPointRepository.findById(id);
        if(agendaPoint.isPresent()) {
            return agendaPoint.get();
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public AgendaPoint create(AgendaPointRequest request) throws NotFoundException {
        return agendaPointRepository.save(new AgendaPoint(request, agendaService.findById(request.getAgendaId())));
    }

    @Override
    public AgendaPoint update(Integer id, AgendaPointRequest request) throws NotFoundException {
        AgendaPoint agendaPoint = findById(id);
        if(request.getNumber() != null) {
            agendaPoint.setNumber(request.getNumber());
        }
        if(request.getTitle() != null) {
            agendaPoint.setTitle(request.getTitle());
        }
        if(request.getDescription() != null) {
            agendaPoint.setDescription(request.getDescription());
        }
        if(request.getStart() != null) {
            agendaPoint.setStart(request.getStart());
        }
        if(request.getEnd() != null) {
            agendaPoint.setEnd(request.getEnd());
        }
        if(request.getActualStart() != null) {
            agendaPoint.setActualStart(request.getActualStart());
        }
        if(request.getActualEnd() != null) {
            agendaPoint.setActualEnd(request.getActualEnd());
        }
        if(request.getStatus() != null) {
            agendaPoint.setStatus(request.getStatus());
        }
        if(request.getAgendaId() != null) {
            agendaPoint.setAgenda(agendaService.findById(request.getAgendaId()));
        }
        return agendaPointRepository.save(agendaPoint);
    }

    @Override
    public AgendaPoint update(AgendaMessage message) throws NotFoundException {
        AgendaPoint agendaPoint = findById(message.getAgendaPointId());

        agendaPoint.setStatus(message.getState().toString());

        if (message.getActualStart() != null) {
            agendaPoint.setActualStart(message.getActualStart());
        }

        if (message.getActualEnd() != null) {
            agendaPoint.setActualEnd(message.getActualEnd());
        }

        return agendaPointRepository.save(agendaPoint);
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        agendaPointRepository.delete(findById(id));
    }
}
