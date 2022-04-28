package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.persistence.data.Position;
import com.erstedigital.meetingappbackend.persistence.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping(path="/positions")
public class PositionController {
    private PositionRepository positionRepository;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    @GetMapping(value = "/findById")
    public @ResponseBody
    Optional<Position> getPositionById(@RequestParam(value = "id") Integer id) {
        return positionRepository.findById(id);
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewPosition(@RequestParam String name,
                                               @RequestParam Integer hourly_cost) {
        Position position = new Position();
        position.setName(name);
        position.setHourly_cost(hourly_cost);

        positionRepository.save(position);
        return "Saved";
    }

    @PutMapping(path="/update")
    public @ResponseBody String updatePosition(@RequestParam Integer id,
                                               @RequestParam Optional<String> name,
                                               @RequestParam Optional<Integer> hourly_cost) {
        Position position = positionRepository.getById(id);
        name.ifPresent(position::setName);
        hourly_cost.ifPresent(position::setHourly_cost);

        positionRepository.save(position);
        return "Updated";
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody String deletePosition(@RequestParam Integer id) {
        if(getPositionById(id).isPresent()) {
            positionRepository.delete(getPositionById(id).get());
        }

        return "Deleted";
    }

    @Autowired
    private void setPositionRepository(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }
}