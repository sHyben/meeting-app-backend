package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.rest.data.request.PositionRequest;
import com.erstedigital.meetingappbackend.rest.service.PositionService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PositionControllerTest {
    @Autowired
    PositionService positionService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private static PositionRequest request;
    private static Integer currentId;

    @BeforeAll
    public static void setUpRequest() {
        request = new PositionRequest();
        request.setName("Test position");
    }

    @Test
    @Order(1)
    void addNewPosition() throws Exception {
        MvcResult result = mockMvc.perform(post("/position")
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
    void getPositionById() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/position/{id}", currentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(currentId));
    }

    @Test
    @Order(3)
    void getPositionById_WrongId() throws Exception  {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/position/{id}", Integer.MAX_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    void getAllPositions() throws Exception {
        mockMvc.perform(get("/position")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    @Order(5)
    void updatePosition() throws Exception {
        PositionRequest newRequest = new PositionRequest();
        newRequest.setName("New test position");

        mockMvc.perform(put("/position/{id}", currentId)
                        .content(mapper.writeValueAsString(newRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newRequest.getName()));
    }

    @Test
    @Order(6)
    void deletePosition() throws Exception {
        mockMvc.perform(delete("/position/{id}", currentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/position/{id}", currentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}