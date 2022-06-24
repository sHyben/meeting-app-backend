package com.erstedigital.meetingappbackend.rest.service.impl;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.AgendaPointState;
import com.erstedigital.meetingappbackend.persistence.data.AgendaPoint;
import com.erstedigital.meetingappbackend.persistence.repository.AgendaPointRepository;
import com.erstedigital.meetingappbackend.rest.data.request.AgendaPointRequest;
import com.erstedigital.meetingappbackend.rest.service.AgendaPointService;
import com.erstedigital.meetingappbackend.rest.service.AgendaService;
import com.erstedigital.meetingappbackend.websockets.model.AgendaMessage;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
    public List<AgendaPoint> create(List<AgendaPointRequest> request) throws NotFoundException {
        ArrayList<AgendaPoint> newAgendaPoints = new ArrayList<>();

        request.forEach(ap -> {
            try {
                newAgendaPoints.add(new AgendaPoint(ap, agendaService.findById(ap.getAgendaId())));
            } catch (NotFoundException e) {}
        });
        return agendaPointRepository.saveAll(newAgendaPoints);
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

    /**
     * Updates times of agenda points based on the end time of previous agenda point
     *
     * @param start start time, according to which times will be adjusted
     * @param agendaPoints which should be adjusted based on start time
     * @return adjusted agenda points
     */
    private List<AgendaPoint> updateTimes(Date start, List<AgendaPoint> agendaPoints) {
        AtomicReference<Date> startTime = new AtomicReference<>(start);
        agendaPoints = agendaPoints.stream().filter(ap -> !ap.getStatus().equals(AgendaPointState.SKIPPED.toString())).peek(ap -> {
            long diffInMillies = Math.abs(ap.getStart().getTime() - ap.getEnd().getTime());
            Date end = new Date(startTime.get().getTime());
            end.setTime(end.getTime() + diffInMillies);
            ap.setStart(startTime.get());
            ap.setEnd(end);
            startTime.set(new Date(end.getTime()));
        }).collect(Collectors.toList());
        return agendaPoints;
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

        if (message.getState() == AgendaPointState.SKIPPED) {
            List<AgendaPoint> agendaPoints = agendaPoint.getAgenda().getAgendaPoints().stream().filter(ap -> ap.getEnd().after(agendaPoint.getEnd())).sorted().collect(Collectors.toList());
            agendaPoints = updateTimes(agendaPoint.getEnd(), agendaPoints);
            agendaPointRepository.saveAll(agendaPoints);
        } else if (message.getState() == AgendaPointState.DONE) {
            List<AgendaPoint> agendaPoints = agendaPoint.getAgenda().getAgendaPoints().stream().filter(ap -> ap.getEnd().after(agendaPoint.getEnd())).sorted().collect(Collectors.toList());
            agendaPoints = updateTimes(agendaPoint.getActualEnd(), agendaPoints);
            if (!agendaPoints.isEmpty()) {
                agendaPoints.get(0).setStatus(AgendaPointState.ONGOING.toString());
                agendaPoints.get(0).setActualStart(new Date());
                agendaPointRepository.save(agendaPoint);
                agendaPoints = agendaPointRepository.saveAll(agendaPoints);
                return agendaPoints.get(0);
            }
        } else if (message.getState() == AgendaPointState.ONGOING) {
            List<AgendaPoint> agendaPoints = agendaPoint.getAgenda().getAgendaPoints().stream().sorted().collect(Collectors.toList());
            agendaPoints = updateTimes(agendaPoint.getActualStart(), agendaPoints);
            if (!agendaPoints.isEmpty()) {
                agendaPointRepository.save(agendaPoint);
                agendaPoints = agendaPointRepository.saveAll(agendaPoints);
                return agendaPoints.get(0);
            }
        }

        return agendaPointRepository.save(agendaPoint);
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        agendaPointRepository.delete(findById(id));
    }
}
