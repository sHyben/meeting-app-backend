package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.rest.data.request.*;
import com.erstedigital.meetingappbackend.rest.service.AgendaPointService;
import com.erstedigital.meetingappbackend.rest.service.AgendaService;
import com.erstedigital.meetingappbackend.rest.service.PositionService;
import com.erstedigital.meetingappbackend.rest.service.UserService;
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

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AgendaPointControllerTest {
    @Autowired
    AgendaPointService agendaPointService;

    @Autowired
    AgendaService agendaService;

    @Autowired
    PositionService positionService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private AgendaPointRequest request;

    @BeforeEach
    public void setUpAgenda() throws Exception {
        PositionRequest positionRequest = new PositionRequest();
        mockMvc.perform(post("/position")
                .content(mapper.writeValueAsString(positionRequest))
                .contentType(MediaType.APPLICATION_JSON));

        UserRequest userRequest = new UserRequest();
        userRequest.setPositionId(1);
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

        AgendaRequest agendaRequest = new AgendaRequest();
        agendaRequest.setMeetingId(1);
        mockMvc.perform(post("/agenda")
                .content(mapper.writeValueAsString(agendaRequest))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @BeforeEach
    public void setUpRequest() {
        request = new AgendaPointRequest();
        request.setAgendaId(1);
        request.setTitle("Test title");
    }

    @Test
    void getAllAgendaPoints() throws Exception {
        mockMvc.perform(post("/agendaPoint")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/agendaPoint")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/agendaPoint")
                        .param("agendaId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    void getAgendaPointById() throws Exception {
        mockMvc.perform(post("/agendaPoint")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/agendaPoint/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.agendaId").value(request.getAgendaId()));
    }

    @Test
    void getAgendaPointById_WrongId() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/agendaPoint/{id}", Integer.MAX_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void addNewAgendaPoint() throws Exception {
        mockMvc.perform(post("/agendaPoint")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.agendaId").value(request.getAgendaId()));
    }

    // TODO
    @Test
    void addNewAgendaPoints() throws Exception {
    }

    @Test
    void updateAgendaPoint() throws Exception {
        AgendaPointRequest newRequest = new AgendaPointRequest();
        newRequest.setTitle("New test title");

        mockMvc.perform(put("/agendaPoint/{id}", 1)
                        .content(mapper.writeValueAsString(newRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New test title"));
    }

    @Test
    void deleteAgendaPoint() throws Exception {
        mockMvc.perform(delete("/agendaPoint/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/agendaPoint/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}