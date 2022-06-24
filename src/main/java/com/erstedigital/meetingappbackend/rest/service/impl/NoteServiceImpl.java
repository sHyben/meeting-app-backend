package com.erstedigital.meetingappbackend.rest.service.impl;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import com.erstedigital.meetingappbackend.persistence.data.Note;
import com.erstedigital.meetingappbackend.persistence.data.User;
import com.erstedigital.meetingappbackend.persistence.repository.MeetingRepository;
import com.erstedigital.meetingappbackend.persistence.repository.NoteRepository;
import com.erstedigital.meetingappbackend.persistence.repository.PositionRepository;
import com.erstedigital.meetingappbackend.persistence.repository.UserRepository;
import com.erstedigital.meetingappbackend.rest.service.ActivityService;
import com.erstedigital.meetingappbackend.rest.service.MeetingService;
import com.erstedigital.meetingappbackend.rest.service.NoteService;
import com.erstedigital.meetingappbackend.rest.service.UserService;
import com.erstedigital.meetingappbackend.websockets.model.NoteMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserService userService;
    private final MeetingService meetingService;


    public NoteServiceImpl(NoteRepository noteRepository, UserService userService, MeetingService meetingService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
        this.meetingService = meetingService;
    }

    @Override
    public List<Note> findByMeetingId(Integer id) {
        return noteRepository.findAllByMeetingId(id);
    }

    @Override
    public Note createNote(NoteMessage noteMessage, Integer meetingId) throws NotFoundException  {
        Meeting meeting = meetingService.findById(meetingId);
        User user = userService.findByEmail(noteMessage.getFrom());

        return noteRepository.save(new Note(meeting, user, noteMessage.getText()));
    }
}

