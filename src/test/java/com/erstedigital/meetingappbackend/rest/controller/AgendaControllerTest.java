package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.rest.data.request.*;
import com.erstedigital.meetingappbackend.rest.service.AgendaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AgendaControllerTest {
    @Autowired
    AgendaService agendaService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private static AgendaRequest request;
    private static Integer currentId;

    @BeforeAll
    public static void setUpRequest() {
        request = new AgendaRequest();
    }

    @Test
    @Order(1)
    void addNewAgenda() throws Exception {
        PositionRequest positionRequest = new PositionRequest();
        MvcResult result1 = mockMvc.perform(post("/position")
                .content(mapper.writeValueAsString(positionRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        String response1 = result1.getResponse().getContentAsString();
        Integer positionId = JsonPath.parse(response1).read("id");

        UserRequest userRequest = new UserRequest();
        userRequest.setPositionId(positionId);
        MvcResult result2 = mockMvc.perform(post("/user")
                .content(mapper.writeValueAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        String response2 = result2.getResponse().getContentAsString();
        Integer userId = JsonPath.parse(response2).read("id");

        MeetingRequest meetingRequest = new MeetingRequest();
        meetingRequest.setOrganizerId(userId);
        meetingRequest.setActivities(new HashSet<>());
        meetingRequest.setAttendees(new HashSet<>());
        MvcResult result3 = mockMvc.perform(post("/meetings")
                .content(mapper.writeValueAsString(meetingRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        String response3 = result3.getResponse().getContentAsString();
        Integer meetingId = JsonPath.parse(response3).read("id");

        request.setMeetingId(meetingId);
        MvcResult result = mockMvc.perform(post("/agenda")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.meetingId").value(request.getMeetingId()))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        currentId = JsonPath.parse(response).read("id");
    }

    @Test
    @Order(2)
    void getAgendaById() throws Exception {
        mockMvc.perform(get("/agenda/{id}", currentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(currentId))
                .andExpect(jsonPath("$.meetingId").value(request.getMeetingId()));
    }

    @Test
    @Order(3)
    void getAgendaById_WrongId() throws Exception  {
        mockMvc.perform(get("/agenda/{id}", Integer.MAX_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    void getAllAgendas() throws Exception {
        mockMvc.perform(get("/agenda")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    @Order(5)
    void updateAgenda() throws Exception {
        AgendaRequest newRequest = new AgendaRequest();
        newRequest.setMeetingId(1);

        mockMvc.perform(put("/agenda/{id}", currentId)
                        .content(mapper.writeValueAsString(newRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(status().isNotFound());
                .andExpect(jsonPath("$.meetingId").value(newRequest.getMeetingId()));
    }

    @Test
    @Order(6)
    void deleteAgenda() throws Exception {
        mockMvc.perform(delete("/agenda/{id}", currentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/agenda/{id}", currentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}