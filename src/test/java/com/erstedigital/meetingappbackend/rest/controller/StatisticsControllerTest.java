package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.rest.data.request.MeetingRequest;
import com.erstedigital.meetingappbackend.rest.data.request.PositionRequest;
import com.erstedigital.meetingappbackend.rest.data.request.StatAttendanceRequest;
import com.erstedigital.meetingappbackend.rest.data.request.UserRequest;
import com.erstedigital.meetingappbackend.rest.service.StatisticsService;
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

import java.sql.Date;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StatisticsControllerTest {
    @Autowired
    StatisticsService statisticsService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private static MeetingRequest request;
    private static StatAttendanceRequest statisticsRequest;
    private static Integer currentId;

    @BeforeAll
    public static void setUpRequest() {
        request = new MeetingRequest();
        request.setOrganizerId(1);
        request.setExchangeId("nmzmvbnvbhjbelasd");
        request.setActivities(new HashSet<>());
        request.setAttendees(new HashSet<>());
        request.setSubject("Test meeting");
        request.setStart(new Date(new java.util.Date().getTime()));
        request.setEnd(new Date(new java.util.Date().getTime()));

        statisticsRequest = new StatAttendanceRequest();
        statisticsRequest.setStart(new Date(new java.util.Date().getTime()));
        statisticsRequest.setEnd(new Date(new java.util.Date().getTime()));

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
        statisticsRequest.setUserId(userId);
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
    void getOrganizerStatistics() throws Exception {
        mockMvc.perform(get("/statistics/organizer")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userId", statisticsRequest.getUserId().toString())
                        .param("start", statisticsRequest.getStart().toString())
                        .param("end", statisticsRequest.getEnd().toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void getAttendeeStatistics() throws Exception {
        mockMvc.perform(get("/statistics/attendee")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userId", statisticsRequest.getUserId().toString())
                        .param("start", statisticsRequest.getStart().toString())
                        .param("end", statisticsRequest.getEnd().toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}