package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.rest.data.request.PositionRequest;
import com.erstedigital.meetingappbackend.rest.data.request.UserRequest;
import com.erstedigital.meetingappbackend.rest.service.UserService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {
    @Autowired
    UserService userService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private static UserRequest request;
    private static Integer currentId;

    @BeforeAll
    public static void setUpRequest() {
        request = new UserRequest();
        request.setName("Test name");
        request.setEmail("test@test.com");
    }

    @Test
    @Order(1)
    void addNewUser() throws Exception {
        PositionRequest positionRequest = new PositionRequest();
        MvcResult result1 = mockMvc.perform(post("/position")
                        .content(mapper.writeValueAsString(positionRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        String response1 = result1.getResponse().getContentAsString();
        Integer positionId = JsonPath.parse(response1).read("id");

        request.setPositionId(positionId);
        MvcResult result = mockMvc.perform(post("/user")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        currentId = JsonPath.parse(response).read("id");
    }

    @Test
    @Order(2)
    void getUserById() throws Exception {
        mockMvc.perform(get("/user/{id}", currentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(currentId))
                .andExpect(jsonPath("$.name").value(request.getName()));
    }

    @Test
    @Order(3)
    void getAgendaById_WrongId() throws Exception  {
        mockMvc.perform(get("/user/{id}", Integer.MAX_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    void getUserByMail() throws Exception {
        mockMvc.perform(get("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("email", request.getEmail()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(currentId))
                .andExpect(jsonPath("$.name").value(request.getName()));
    }

    @Test
    @Order(5)
    void updateUser() throws Exception {
        UserRequest newRequest = new UserRequest();
        newRequest.setName("New test name");

        mockMvc.perform(put("/user/{id}", currentId)
                        .content(mapper.writeValueAsString(newRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newRequest.getName()));
    }

    @Test
    @Order(6)
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/user/{id}", currentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/user/{id}", currentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void addNewUsers() throws Exception {
        UserRequest newRequest = new UserRequest();
        newRequest.setName("New test name");
        List<UserRequest> list = Arrays.asList(request, newRequest);

        mockMvc.perform(post("/user/attendees")
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