package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.persistence.data.Agenda;
import com.erstedigital.meetingappbackend.persistence.repository.AgendaRepository;
import com.erstedigital.meetingappbackend.persistence.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping(path="/agendas")
public class AgendaController {
    private AgendaRepository agendaRepository;
    private MeetingRepository meetingRepository;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Agenda> getAllAgendas() {
        return agendaRepository.findAll();
    }

    @GetMapping(value = "/findById")
    public @ResponseBody
    Optional<Agenda> getAgendaById(@RequestParam(value = "id") Integer id) {
        return agendaRepository.findById(id);
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewAgenda(@RequestParam Integer meetingId) {
        Agenda agenda = new Agenda();
        if(meetingRepository.findById(meetingId).isPresent()) {
            agenda.setAgenda_meeting(meetingRepository.findById(meetingId).get());
        }

        agendaRepository.save(agenda);
        return "Saved";
    }

    @PutMapping(path="/update")
    public @ResponseBody String updateAgenda(@RequestParam Integer id, @RequestParam Optional<Integer> meetingId) {
        Agenda agenda = agendaRepository.getById(id);
        meetingId.ifPresent(integer -> agenda.setAgenda_meeting(meetingRepository.getById(integer)));

        agendaRepository.save(agenda);
        return "Updated";
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody String deleteAgenda(@RequestParam Integer id) {
        if(getAgendaById(id).isPresent()) {
            agendaRepository.delete(getAgendaById(id).get());
        }

        return "Deleted";
    }

    @Autowired
    private void setAgendaRepository(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }
    
    @Autowired
    public void setMeetingRepository(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }
}


