package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.rest.data.request.AgendaRequest;
import com.erstedigital.meetingappbackend.rest.data.request.MeetingRequest;
import com.erstedigital.meetingappbackend.rest.data.request.PositionRequest;
import com.erstedigital.meetingappbackend.rest.data.request.UserRequest;
import com.erstedigital.meetingappbackend.rest.service.AgendaService;
import com.erstedigital.meetingappbackend.rest.service.MeetingService;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MeetingControllerTest {
    @Autowired
    MeetingService meetingService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private static MeetingRequest request;
    private static Integer currentId;

    @BeforeAll
    public static void setUpRequest() {
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
    @Order(1)
    void addNewMeeting() throws Exception {
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

        request.setOrganizerId(userId);
        MvcResult result = mockMvc.perform(post("/meetings")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.organizerId").value(request.getOrganizerId()))
                .andExpect(jsonPath("$.subject").value(request.getSubject()))
                .andExpect(jsonPath("$.exchangeId").value(request.getExchangeId()))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        currentId = JsonPath.parse(response).read("id");
    }

    @Test
    @Order(2)
    void getMeetingById() throws Exception {
        mockMvc.perform(get("/meetings/{id}", currentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(currentId))
                .andExpect(jsonPath("$.organizerId").value(request.getOrganizerId()))
                .andExpect(jsonPath("$.subject").value(request.getSubject()))
                .andExpect(jsonPath("$.exchangeId").value(request.getExchangeId()));
    }

    @Test
    @Order(3)
    void getMeetingById_WrongId() throws Exception  {
        mockMvc.perform(get("/meetings/{id}", Integer.MAX_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    void getMeetingByExchangeId() throws Exception {
        mockMvc.perform(get("/meetings/exchange/{id}", request.getExchangeId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(currentId))
                .andExpect(jsonPath("$.organizerId").value(request.getOrganizerId()))
                .andExpect(jsonPath("$.subject").value(request.getSubject()))
                .andExpect(jsonPath("$.exchangeId").value(request.getExchangeId()));
    }

    @Test
    @Order(5)
    void getAllMeetings() throws Exception {
        mockMvc.perform(get("/meetings")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    @Order(6)
    void updateMeeting() throws Exception {
        MeetingRequest newRequest = new MeetingRequest();
        newRequest.setSubject("New test meeting");

        mockMvc.perform(put("/meetings/{id}", currentId)
                        .content(mapper.writeValueAsString(newRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value(newRequest.getSubject()));
    }

    @Test
    @Order(7)
    void getMeetingsBetweenDatesFromUser() throws Exception {
    }

    @Test
    @Order(8)
    void deleteMeeting() throws Exception {
        mockMvc.perform(delete("/meetings/{id}", currentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/meetings/{id}", currentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}