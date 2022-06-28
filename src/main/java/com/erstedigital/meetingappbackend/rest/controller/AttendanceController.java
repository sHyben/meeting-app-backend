package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.rest.data.request.AttendanceRequest;
import com.erstedigital.meetingappbackend.rest.data.response.AttendanceResponse;
import com.erstedigital.meetingappbackend.rest.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"https://www.bettermeetings.sk", "http://localhost:3000"}, maxAge = 3600)
@RestController
@RequestMapping(path="/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping
    public @ResponseBody
    List<AttendanceResponse> getAllAttendances() {
        return attendanceService.getAll().stream().map(AttendanceResponse::new).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    AttendanceResponse getAttendanceById(@PathVariable("id") Integer id) throws NotFoundException {
        return new AttendanceResponse(attendanceService.findById(id));
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<AttendanceResponse> addNewAttendance(@RequestBody AttendanceRequest body) throws NotFoundException {
        return new ResponseEntity<>(new AttendanceResponse(attendanceService.create(body)), HttpStatus.CREATED);
    }

    @PutMapping(path="/{id}")
    public @ResponseBody
    AttendanceResponse
    updateAttendance(@PathVariable("id") Integer id, @RequestBody AttendanceRequest body) throws NotFoundException {
        return new AttendanceResponse(attendanceService.update(id, body));
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody void deleteAttendance(@PathVariable("id") Integer id) throws NotFoundException {
        attendanceService.delete(id);
    }
}

