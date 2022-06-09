package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.rest.data.request.AgendaPointRequest;
import com.erstedigital.meetingappbackend.rest.data.response.AgendaPointResponse;
import com.erstedigital.meetingappbackend.rest.service.AgendaPointService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "https://www.bettermeetings.sk", maxAge = 3600)
@RestController
@RequestMapping(path="/agendaPoint")
public class AgendaPointController {
    private final AgendaPointService agendaPointService;

    public AgendaPointController(AgendaPointService agendaPointService) {
        this.agendaPointService = agendaPointService;
    }

    @GetMapping
    public @ResponseBody
    List<AgendaPointResponse> getAllAgendaPoints() {
        return agendaPointService.getAll().stream().map(AgendaPointResponse::new).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    AgendaPointResponse getAgendaPointById(@PathVariable("id") Integer id) throws NotFoundException {
        return new AgendaPointResponse(agendaPointService.findById(id));
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<AgendaPointResponse> addNewAgendaPoint(@RequestBody AgendaPointRequest body) throws NotFoundException {
        return new ResponseEntity<>(new AgendaPointResponse(agendaPointService.create(body)), HttpStatus.CREATED);
    }

    @PutMapping(path="/{id}")
    public @ResponseBody AgendaPointResponse updateAgendaPoint(@PathVariable("id") Integer id, @RequestBody AgendaPointRequest body) throws NotFoundException {
        return new AgendaPointResponse(agendaPointService.update(id, body));
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody void deleteAgendaPoint(@PathVariable("id") Integer id) throws NotFoundException {
        agendaPointService.delete(id);
    }
}


