package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.rest.data.request.MeetingRequest;
import com.erstedigital.meetingappbackend.rest.data.request.StatAttendanceRequest;
import com.erstedigital.meetingappbackend.rest.data.response.MeetingResponse;
import com.erstedigital.meetingappbackend.rest.data.response.StatAttendanceResponse;
import com.erstedigital.meetingappbackend.rest.service.MeetingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"https://www.bettermeetings.sk","http://localhost:3000"}, maxAge = 3600)
@RestController
@RequestMapping(path="/meetings")
public class MeetingController {
    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping
    public @ResponseBody
    List<MeetingResponse> getAllMeetings(@RequestParam(name = "userId", required = false) Integer userId) throws NotFoundException {
        if (userId != null) {
            return meetingService.getAll(userId).stream().map(MeetingResponse::new).collect(Collectors.toList());
        } else {
            return meetingService.getAll().stream().map(MeetingResponse::new).collect(Collectors.toList());
        }
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    MeetingResponse getMeetingById(@PathVariable("id") Integer id) throws NotFoundException {
        return new MeetingResponse(meetingService.findById(id));
    }

    @GetMapping(value = "/exchange/{id}")
    public @ResponseBody
    MeetingResponse getMeetingByExchangeId(@PathVariable("id") String id) throws NotFoundException {
        return new MeetingResponse(meetingService.findByExchangeId(id));
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<MeetingResponse> addNewMeeting(@RequestBody MeetingRequest body) throws NotFoundException {
        return new ResponseEntity<>(new MeetingResponse(meetingService.create(body)), HttpStatus.CREATED);
    }

    @PutMapping(path="/{id}")
    public @ResponseBody MeetingResponse updateMeeting(@PathVariable("id") Integer id,
                                                       @RequestBody MeetingRequest body) throws NotFoundException {
        return new MeetingResponse(meetingService.update(id, body));
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody void deleteMeeting(@PathVariable("id") Integer id) throws NotFoundException {
        meetingService.delete(id);
    }

    @GetMapping(value = "/statistics/meetings")
    public @ResponseBody
    StatAttendanceResponse getMeetingsBetweenDatesFromUser(@RequestBody StatAttendanceRequest body) throws NotFoundException {
        return new StatAttendanceResponse(meetingService.getOrganizerMeetings(body.getUserId(), body.getStart(), body.getEnd()));
    }

}
