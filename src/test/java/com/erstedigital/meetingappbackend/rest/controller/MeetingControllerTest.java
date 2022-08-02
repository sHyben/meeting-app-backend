package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.rest.data.request.AgendaRequest;
import com.erstedigital.meetingappbackend.rest.data.request.MeetingRequest;
import com.erstedigital.meetingappbackend.rest.data.request.PositionRequest;
import com.erstedigital.meetingappbackend.rest.data.request.UserRequest;
import com.erstedigital.meetingappbackend.rest.service.AgendaService;
import com.erstedigital.meetingappbackend.rest.service.MeetingService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MeetingControllerTest {
    @Autowired
    MeetingService meetingService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private MeetingRequest request;

    @BeforeEach
    public void setUpUser() throws Exception {
        PositionRequest positionRequest = new PositionRequest();
        mockMvc.perform(post("/position")
                .content(mapper.writeValueAsString(positionRequest))
                .contentType(MediaType.APPLICATION_JSON));

        UserRequest userRequest = new UserRequest();
        userRequest.setPositionId(1);
        mockMvc.perform(post("/user")
                .content(mapper.writeValueAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @BeforeEach
    public void setUpRequest() {
        request = new MeetingRequest();
        request.setOrganizerId(1);
        request.setExchangeId("qwertyuiop1235zxcvbnm");
        request.setActivities(new HashSet<>());
        request.setAttendees(new HashSet<>());
        request.setSubject("Test meeting");
        request.setStart(new Date(new java.util.Date().getTime()));
        request.setEnd(new Date(new java.util.Date().getTime()));
    }

    @Test
    void getAllMeetings() throws Exception {
        mockMvc.perform(post("/meetings")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/meetings")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/meetings")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    void getMeetingById() throws Exception {
        mockMvc.perform(post("/meetings")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/meetings/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.organizerId").value(request.getOrganizerId()))
                .andExpect(jsonPath("$.subject").value(request.getSubject()))
                .andExpect(jsonPath("$.exchangeId").value(request.getExchangeId()));
    }

    @Test
    void getMeetingById_WrongId() throws Exception  {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/meetings/{id}", Integer.MAX_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getMeetingByExchangeId() throws Exception {
        request.setExchangeId("anjkbfjhdbahjbfhjabvfhvabhf");
        mockMvc.perform(post("/meetings")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/meetings/exchange/{id}", request.getExchangeId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.organizerId").value(request.getOrganizerId()))
                .andExpect(jsonPath("$.subject").value(request.getSubject()))
                .andExpect(jsonPath("$.exchangeId").value(request.getExchangeId()));

    }

    @Test
    void addNewMeeting() throws Exception {
        mockMvc.perform(post("/meetings")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.organizerId").value(request.getOrganizerId()))
                .andExpect(jsonPath("$.subject").value(request.getSubject()))
                .andExpect(jsonPath("$.exchangeId").value(request.getExchangeId()));
    }

    @Test
    void updateMeeting() throws Exception {
        MeetingRequest newRequest = new MeetingRequest();
        newRequest.setSubject("New test meeting");

        mockMvc.perform(put("/meetings/{id}", 1)
                        .content(mapper.writeValueAsString(newRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value(newRequest.getSubject()));
    }

    @Test
    void deleteMeeting() throws Exception {
        mockMvc.perform(delete("/meetings/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/meetings/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getMeetingsBetweenDatesFromUser() throws Exception {
    }
}