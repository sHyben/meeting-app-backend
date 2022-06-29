package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.rest.data.request.AgendaRequest;
import com.erstedigital.meetingappbackend.rest.data.response.AgendaResponse;
import com.erstedigital.meetingappbackend.rest.service.AgendaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = {"https://www.bettermeetings.sk","http://localhost:3000"}, maxAge = 3600)
@RestController
@RequestMapping(path="/agenda")
public class AgendaController {
    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @GetMapping
    public @ResponseBody
    List<AgendaResponse> getAllAgendas() {
        return agendaService.getAll().stream().map(AgendaResponse::new).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    AgendaResponse getAgendaById(@PathVariable("id") Integer id) throws NotFoundException {
        return new AgendaResponse(agendaService.findById(id));
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<AgendaResponse> addNewAgenda(@RequestBody AgendaRequest body) throws NotFoundException {
        return new ResponseEntity<>(new AgendaResponse(agendaService.create(body)), HttpStatus.CREATED);
    }

    @PutMapping(path="/{id}")
    public @ResponseBody
    AgendaResponse updateAgenda(@PathVariable("id") Integer id, @RequestBody AgendaRequest body) throws NotFoundException {
        return new AgendaResponse(agendaService.update(id, body));
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody void deleteAgenda(@PathVariable("id") Integer id) throws NotFoundException {
        agendaService.delete(id);
    }
}


