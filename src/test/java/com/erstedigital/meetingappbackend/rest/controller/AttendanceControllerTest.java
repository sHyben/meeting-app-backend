package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.rest.data.request.*;
import com.erstedigital.meetingappbackend.rest.service.AttendanceService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AttendanceControllerTest {
    @Autowired
    AttendanceService attendanceService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private AttendanceRequest request;

    @BeforeEach
    public void setUpMeeting() throws Exception {
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
    }

    @BeforeEach
    public void setUpRequest() {
        request = new AttendanceRequest();
        request.setMeetingId(1);
        request.setUserId(1);
        request.setPresenceTime(30);
    }

    @Test
    void getAllAttendances() throws Exception {
        mockMvc.perform(post("/attendance")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/attendance")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/attendance")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    void getAttendanceById() throws Exception {
        mockMvc.perform(post("/attendance")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/attendance/{id}", 11)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(11))
                .andExpect(jsonPath("$.meetingId").value(request.getMeetingId()))
                .andExpect(jsonPath("$.userId").value(request.getUserId()));
    }

    @Test
    void getAttendanceById_WrongId() throws Exception  {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/attendance/{id}", Integer.MAX_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addNewAttendance() throws Exception {
        mockMvc.perform(post("/attendance")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.meetingId").value(request.getMeetingId()))
                .andExpect(jsonPath("$.userId").value(request.getUserId()));
    }

    @Test
    void updateAttendance() throws Exception {
        AttendanceRequest newRequest = new AttendanceRequest();
        newRequest.setMeetingId(3);
        newRequest.setPresenceTime(60);

        mockMvc.perform(post("/attendance")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        mockMvc.perform(put("/attendance/{id}", 5)
                        .content(mapper.writeValueAsString(newRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meetingId").value(newRequest.getMeetingId()))
                .andExpect(jsonPath("$.presenceTime").value(newRequest.getPresenceTime()));
    }

    @Test
    void deleteAttendance() throws Exception {
        mockMvc.perform(delete("/attendance/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/attendance/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}