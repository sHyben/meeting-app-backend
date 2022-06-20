package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.rest.data.response.AgendaPointResponse;
import com.erstedigital.meetingappbackend.rest.data.response.NoteResponse;
import com.erstedigital.meetingappbackend.rest.service.MeetingService;
import com.erstedigital.meetingappbackend.rest.service.NoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping(path="/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping(value = "")
    public @ResponseBody
    List<NoteResponse> getNotesByMeetingId(@RequestParam("meetingId") Integer id) throws NotFoundException {
        return noteService.findByMeetingId(id).stream().map(NoteResponse::new).collect(Collectors.toList());
    }
}
