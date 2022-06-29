package com.erstedigital.meetingappbackend.rest.service;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Note;
import com.erstedigital.meetingappbackend.websockets.model.NoteMessage;

import java.util.List;

public interface NoteService {

    List<Note> findByMeetingId(Integer id);

    Note createNote(NoteMessage note, Integer meetingId) throws NotFoundException;
}
