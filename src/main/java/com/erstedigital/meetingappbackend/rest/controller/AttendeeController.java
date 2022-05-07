package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.rest.data.request.AttendeeRequest;
import com.erstedigital.meetingappbackend.rest.data.response.AttendeeResponse;
import com.erstedigital.meetingappbackend.rest.service.AttendeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping(path="/attendee")
public class AttendeeController {
    private final AttendeeService attendeeService;

    public AttendeeController(AttendeeService attendeeService) {
        this.attendeeService = attendeeService;
    }

    @GetMapping
    public @ResponseBody
    List<AttendeeResponse> getAllAttendees() {
        return attendeeService.getAll().stream().map(AttendeeResponse::new).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    AttendeeResponse getAttendeeById(@PathVariable("id") Integer id) throws NotFoundException {
        return new AttendeeResponse(attendeeService.findById(id));
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<AttendeeResponse> addNewAttendee(@RequestBody AttendeeRequest body) throws NotFoundException {
        return new ResponseEntity<>(new AttendeeResponse(attendeeService.create(body)), HttpStatus.CREATED);
    }

    @PutMapping(path="/{id}")
    public @ResponseBody AttendeeResponse
    updateAttendee(@PathVariable("id") Integer id, @RequestBody AttendeeRequest body) throws NotFoundException {
        return new AttendeeResponse(attendeeService.update(id, body));
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody void deleteAttendee(@PathVariable("id") Integer id) throws NotFoundException {
        attendeeService.delete(id);
    }
}

