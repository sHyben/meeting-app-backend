package com.erstedigital.meetingappbackend.websockets.controller;

import com.erstedigital.meetingappbackend.websockets.model.AgendaMessage;
import com.erstedigital.meetingappbackend.websockets.model.NoteMessage;
import com.erstedigital.meetingappbackend.websockets.response.AgendaOutputMessage;
import com.erstedigital.meetingappbackend.websockets.response.NoteOutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WebSocketController {

    @MessageMapping("/note/{id}")
    @SendTo("/note/messages/{id}")
    public NoteOutputMessage sendNote(NoteMessage message) {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new NoteOutputMessage(message.getFrom(), message.getText(), time);
    }

    @MessageMapping("/agenda/{id}")
    @SendTo("/agenda/messages/{id}")
    public AgendaOutputMessage sendAgendaPoint(AgendaMessage message) {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new AgendaOutputMessage(message.getAgendaPointId(), message.getState(), time);
    }
}