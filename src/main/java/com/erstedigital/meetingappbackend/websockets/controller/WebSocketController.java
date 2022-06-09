package com.erstedigital.meetingappbackend.websockets.controller;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.AgendaPointState;
import com.erstedigital.meetingappbackend.persistence.data.AgendaPoint;
import com.erstedigital.meetingappbackend.persistence.data.Note;
import com.erstedigital.meetingappbackend.rest.data.response.AgendaPointResponse;
import com.erstedigital.meetingappbackend.rest.service.AgendaPointService;
import com.erstedigital.meetingappbackend.rest.service.NoteService;
import com.erstedigital.meetingappbackend.websockets.model.AgendaMessage;
import com.erstedigital.meetingappbackend.websockets.model.NoteMessage;
import com.erstedigital.meetingappbackend.websockets.response.AgendaOutputMessage;
import com.erstedigital.meetingappbackend.websockets.response.NoteOutputMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@Controller
public class WebSocketController {

    private final NoteService noteService;
    private final AgendaPointService agendaPointService;
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(NoteService noteService, AgendaPointService agendaPointService, SimpMessagingTemplate messagingTemplate) {
        this.noteService = noteService;
        this.messagingTemplate = messagingTemplate;
        this.agendaPointService = agendaPointService;
    }

    @MessageMapping("/note/{meetingId}")
    public void sendNote(@DestinationVariable Integer meetingId, @Payload NoteMessage message) throws NotFoundException {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        Note newNote = noteService.createNote(message, meetingId);

        messagingTemplate.convertAndSend(
                "/note/messages/" + newNote,
                new NoteOutputMessage(newNote.getId(), newNote.getFrom().getEmail(), newNote.getText(), time)
        );
    }

    @MessageMapping("/agenda/{meetingId}")
    public void sendAgendaPoint(@DestinationVariable Integer meetingId, @Payload AgendaMessage message) throws NotFoundException {
        String time = new SimpleDateFormat("HH:mm").format(new Date());

        AgendaPoint agendaPoint = agendaPointService.update(message);

        messagingTemplate.convertAndSend(
                "/agenda/messages/" + meetingId,
                new AgendaOutputMessage(
                        agendaPoint.getId(),
                        AgendaPointState.valueOf(agendaPoint.getStatus()),
                        agendaPoint.getActualStart(),
                        agendaPoint.getActualEnd()
                )
        );
    }
}