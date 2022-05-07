package com.erstedigital.meetingappbackend.rest.service.impl;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Position;
import com.erstedigital.meetingappbackend.persistence.repository.PositionRepository;
import com.erstedigital.meetingappbackend.rest.data.request.PositionRequest;
import com.erstedigital.meetingappbackend.rest.service.PositionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PositionServiceImpl implements PositionService {
    private final PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public List<Position> getAll() {
        return positionRepository.findAll();
    }

    @Override
    public Position findById(Integer id) throws NotFoundException {
        Optional<Position> position = positionRepository.findById(id);
        if(position.isPresent()) {
            return position.get();
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public Position create(PositionRequest request) {
        return positionRepository.save(new Position(request));
    }

    @Override
    public Position update(Integer id, PositionRequest request) throws NotFoundException {
        Position position = findById(id);
        if(request.getName() != null) {
            position.setName(request.getName());
        }
        if(request.getHourlyCost() != null) {
            position.setHourly_cost(request.getHourlyCost());
        }
        return positionRepository.save(position);
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        positionRepository.delete(findById(id));
    }
}
