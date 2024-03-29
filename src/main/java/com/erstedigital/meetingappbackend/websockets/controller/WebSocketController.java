package com.erstedigital.meetingappbackend.websockets.controller;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.AgendaPointState;
import com.erstedigital.meetingappbackend.persistence.data.AgendaPoint;
import com.erstedigital.meetingappbackend.persistence.data.Attendance;
import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import com.erstedigital.meetingappbackend.persistence.data.Note;
import com.erstedigital.meetingappbackend.rest.service.AgendaPointService;
import com.erstedigital.meetingappbackend.rest.service.AttendanceService;
import com.erstedigital.meetingappbackend.rest.service.MeetingService;
import com.erstedigital.meetingappbackend.rest.service.NoteService;
import com.erstedigital.meetingappbackend.websockets.model.*;
import com.erstedigital.meetingappbackend.websockets.response.*;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(origins = {"https://www.bettermeetings.sk","http://localhost:3000"}, maxAge = 3600)
@Controller
public class WebSocketController {

    private final NoteService noteService;
    private final AgendaPointService agendaPointService;
    private final MeetingService meetingService;
    private final AttendanceService attendanceService;
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(NoteService noteService, AgendaPointService agendaPointService, MeetingService meetingService, AttendanceService attendanceService, SimpMessagingTemplate messagingTemplate) {
        this.noteService = noteService;
        this.messagingTemplate = messagingTemplate;
        this.agendaPointService = agendaPointService;
        this.meetingService = meetingService;
        this.attendanceService = attendanceService;
    }

    @MessageMapping("/note/{meetingId}")
    public void sendNote(@DestinationVariable Integer meetingId, @Payload NoteMessage message) throws NotFoundException {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        Note newNote = noteService.createNote(message, meetingId);

        messagingTemplate.convertAndSend(
                "/note/messages/" + meetingId,
                new NoteOutputMessage(newNote.getId(), newNote.getFrom().getEmail(), newNote.getText(), time)
        );
    }

    @MessageMapping("/meeting/{meetingId}")
    public void editMeeting(@DestinationVariable Integer meetingId, @Payload MeetingMessage message) throws NotFoundException {
        Meeting newMeeting = meetingService.update(meetingId, message);

        messagingTemplate.convertAndSend(
                "/meeting/messages/" + meetingId,
                new MeetingOutputMessage(newMeeting.getId(), newMeeting.getActualStart(), newMeeting.getActualEnd(), newMeeting.getAnticipatedEndTime())
        );
    }

    @MessageMapping("/activity/{meetingId}")
    public void startActivity(@DestinationVariable Integer meetingId, @Payload ActivityMessage message) throws NotFoundException {
        Integer runningActivityId = meetingService.startActivity(message.getActivityId(), meetingId);

        messagingTemplate.convertAndSend(
                "/activity/messages/" + meetingId,
                new ActivityOutputMessage(runningActivityId)
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
                        agendaPoint.getActualEnd(),
                        agendaPoint.getAnticipatedStart(),
                        agendaPoint.getAnticipatedEnd()
                )
        );
    }

    @MessageMapping("/attendance/{meetingId}")
    public void saveAttendance(@DestinationVariable Integer meetingId, @Payload AttendanceMessage message) throws NotFoundException {

        Attendance attendance = attendanceService.update(message);

        messagingTemplate.convertAndSend(
                "/attendance/messages/" + meetingId,
                new AttendanceOutputMessage(
                        attendance
                )
        );
    }
}