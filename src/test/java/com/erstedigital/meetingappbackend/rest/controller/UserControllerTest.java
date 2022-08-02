package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.rest.data.request.AgendaRequest;
import com.erstedigital.meetingappbackend.rest.data.request.MeetingRequest;
import com.erstedigital.meetingappbackend.rest.data.request.PositionRequest;
import com.erstedigital.meetingappbackend.rest.data.request.UserRequest;
import com.erstedigital.meetingappbackend.rest.service.AgendaService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    @Autowired
    UserService userService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private UserRequest request;

    @BeforeEach
    public void setUpPosition() throws Exception {
        PositionRequest positionRequest = new PositionRequest();
        mockMvc.perform(post("/position")
                .content(mapper.writeValueAsString(positionRequest))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @BeforeEach
    public void setUpRequest() throws Exception {
        request = new UserRequest();
        request.setPositionId(1);
        request.setName("Test name");
    }

    @Test
    void getUserByMail() throws Exception {
        request.setEmail("test@test.com");

        mockMvc.perform(post("/user")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("email", "test@test.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value(request.getName()));
    }

    @Test
    void getUserById() throws Exception {
        mockMvc.perform(post("/user")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/user/{id}", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value(request.getName()));
    }

    @Test
    void getAgendaById_WrongId() throws Exception  {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/{id}", Integer.MAX_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addNewUser() throws Exception {
        mockMvc.perform(post("/user")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(request.getName()));
    }

    @Test
    void updateUser() throws Exception {
        UserRequest newRequest = new UserRequest();
        newRequest.setName("New test name");

        mockMvc.perform(put("/user/{id}", 1)
                        .content(mapper.writeValueAsString(newRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newRequest.getName()));
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/user/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/user/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void addNewUsers() throws Exception {
        UserRequest newRequest = new UserRequest();
        newRequest.setName("New test name");
        List<UserRequest> list = Arrays.asList(request, newRequest);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/user/attendees")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(list)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }

    // TODO
    @Test
    void getMeetingAttendees() throws Exception {
    }
}