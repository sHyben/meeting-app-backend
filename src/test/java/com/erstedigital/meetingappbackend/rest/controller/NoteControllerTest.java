package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.rest.data.request.MeetingRequest;
import com.erstedigital.meetingappbackend.rest.data.request.PositionRequest;
import com.erstedigital.meetingappbackend.rest.data.request.UserRequest;
import com.erstedigital.meetingappbackend.rest.service.NoteService;
import com.erstedigital.meetingappbackend.websockets.model.NoteMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class NoteControllerTest {
    @Autowired
    NoteService noteService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private NoteMessage message;

    @BeforeEach
    public void setUpMeeting() throws Exception {
        PositionRequest positionRequest = new PositionRequest();
        mockMvc.perform(post("/position")
                .content(mapper.writeValueAsString(positionRequest))
                .contentType(MediaType.APPLICATION_JSON));

        UserRequest userRequest = new UserRequest();
        userRequest.setPositionId(1);
        userRequest.setEmail("test@test.com");

        mockMvc.perform(post("/user")
                .content(mapper.writeValueAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON));

        MeetingRequest meetingRequest = new MeetingRequest();
        meetingRequest.setOrganizerId(1);
        meetingRequest.setActivities(new HashSet<>());
        meetingRequest.setAttendees(new HashSet<>());
        mockMvc.perform(post("/meetings")
                .content(mapper.writeValueAsString(meetingRequest))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @BeforeEach
    public void setUpRequest() throws NotFoundException {
        message = new NoteMessage();
        message.setFrom("test@test.com");
        message.setText("Test message");
    }

    @Test
    void getNotesByMeetingId() throws Exception {
        noteService.createNote(message, 1);
        mockMvc.perform(get("/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("meetingId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }
}