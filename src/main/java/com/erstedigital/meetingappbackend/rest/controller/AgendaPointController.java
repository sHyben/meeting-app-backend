package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.persistence.data.AgendaPoint;
import com.erstedigital.meetingappbackend.persistence.repository.AgendaPointRepository;
import com.erstedigital.meetingappbackend.persistence.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping(path="/agendaPoints")
public class AgendaPointController {
    private AgendaPointRepository agendaPointRepository;
    private AgendaRepository agendaRepository;

    @GetMapping(path="/all")
    public @ResponseBody
    Iterable<AgendaPoint> getAllAgendaPoints() {
        return agendaPointRepository.findAll();
    }

    @GetMapping(value = "/findById")
    public @ResponseBody
    Optional<AgendaPoint> getAgendaPointById(@RequestParam(value = "id") Integer id) {
        return agendaPointRepository.findById(id);
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewAgendaPoint(@RequestParam Integer number, @RequestParam String title,
                                                  @RequestParam String description, @RequestParam Time duration,
                                                  @RequestParam String status, @RequestParam Integer agendaId) {
        AgendaPoint agendaPoint = new AgendaPoint();
        agendaPoint.setNumber(number);
        agendaPoint.setTitle(title);
        agendaPoint.setDescription(description);
        agendaPoint.setDuration(duration);
        agendaPoint.setStatus(status);
        if(agendaRepository.findById(agendaId).isPresent()) {
            agendaPoint.setAgenda_points_id(agendaRepository.findById(agendaId).get());
        }

        agendaPointRepository.save(agendaPoint);
        return "Saved";
    }

    @PutMapping(path="/update")
    public @ResponseBody String updateUser(@RequestParam Integer id, @RequestParam Optional<Integer> number,
                                           @RequestParam Optional<String> title, @RequestParam Optional<String> description,
                                           @RequestParam Optional<Time> duration, @RequestParam Optional<String> status,
                                           @RequestParam Optional<Integer> agendaId) {
        AgendaPoint agendaPoint = agendaPointRepository.getById(id);
        number.ifPresent(agendaPoint::setNumber);
        title.ifPresent(agendaPoint::setTitle);
        description.ifPresent(agendaPoint::setDescription);
        duration.ifPresent(agendaPoint::setDuration);
        status.ifPresent(agendaPoint::setStatus);
        agendaId.ifPresent(integer -> agendaPoint.setAgenda_points_id(agendaRepository.getById(integer)));

        agendaPointRepository.save(agendaPoint);
        return "Updated";
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody String deleteUser (@RequestParam Integer id) {
        if(getAgendaPointById(id).isPresent()) {
            agendaPointRepository.delete(getAgendaPointById(id).get());
        }

        return "Deleted";
    }

    @Autowired
    private void setAgendaPointRepository(AgendaPointRepository agendaPointRepository) {
        this.agendaPointRepository = agendaPointRepository;
    }

    @Autowired
    private void setAgendaRepository(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }
}


