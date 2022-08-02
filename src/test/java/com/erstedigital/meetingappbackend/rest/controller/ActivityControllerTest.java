package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.rest.data.request.ActivityRequest;
import com.erstedigital.meetingappbackend.rest.service.ActivityService;
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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ActivityControllerTest {

    @Autowired
    ActivityService activityService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private ActivityRequest request;

    @BeforeEach
    public void setUpRequest() {
        request = new ActivityRequest();
        request.setType("Test Type");
        request.setTitle("Test title");
        request.setText("Test text");
        request.setAnswer("Test answer");
        request.setImgUrl("www.test.net");
    }

    @Test
    void getAllActivities() throws Exception {
        mockMvc.perform(post("/activity")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(post("/activity")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/activity")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    public void getActivityById() throws Exception {

        mockMvc.perform(post("/activity")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/activity/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value(request.getType()))
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andExpect(jsonPath("$.text").value(request.getText()))
                .andExpect(jsonPath("$.answer").value(request.getAnswer()))
                .andExpect(jsonPath("$.imgUrl").value(request.getImgUrl()));
    }

    @Test
    void getActivityById_WrongId() throws Exception {
        mockMvc.perform(get("/activity/{id}", Integer.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void addNewActivity() throws Exception {
        mockMvc.perform(post("/activity")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value(request.getType()))
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andExpect(jsonPath("$.text").value(request.getText()))
                .andExpect(jsonPath("$.answer").value(request.getAnswer()))
                .andExpect(jsonPath("$.imgUrl").value(request.getImgUrl()));
    }

    @Test
    void updateActivity() throws Exception {
        ActivityRequest newRequest = new ActivityRequest();
        newRequest.setText("New text");

        mockMvc.perform(post("/activity")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print());

        mockMvc.perform(put("/activity/{id}", 1)
                .content(mapper.writeValueAsString(newRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("New text"))
                .andExpect(jsonPath("$.type").value(request.getType()))
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andExpect(jsonPath("$.answer").value(request.getAnswer()))
                .andExpect(jsonPath("$.imgUrl").value(request.getImgUrl()));
    }

    @Test
    void deleteActivity() throws Exception {
        mockMvc.perform(delete("/activity/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform( MockMvcRequestBuilders
                        .get("/activity/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteActivity_NotFound() throws Exception {
        mockMvc.perform(delete("/activity/{id}", 10)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}