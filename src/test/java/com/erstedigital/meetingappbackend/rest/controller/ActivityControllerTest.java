package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.rest.data.request.ActivityRequest;
import com.erstedigital.meetingappbackend.rest.service.ActivityService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ActivityControllerTest {
    @Autowired
    ActivityService activityService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private static ActivityRequest request;
    private static Integer currentId;

    @BeforeAll
    public static void setUpRequest() {
        request = new ActivityRequest();
        request.setType("Test Type");
        request.setTitle("Test title");
        request.setText("Test text");
        request.setAnswer("Test answer");
        request.setImgUrl("www.test.net");
    }

    @Test
    @Order(1)
    void addNewActivity() throws Exception {
        MvcResult result = mockMvc.perform(post("/activity")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value(request.getType()))
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andExpect(jsonPath("$.text").value(request.getText()))
                .andExpect(jsonPath("$.answer").value(request.getAnswer()))
                .andExpect(jsonPath("$.imgUrl").value(request.getImgUrl()))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        //Integer id = JsonPath.parse(response).read("$[0].id");
        currentId = JsonPath.parse(response).read("id");
    }

    @Test
    @Order(2)
    public void getActivityById() throws Exception {
        mockMvc.perform(get("/activity/{id}", currentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(currentId))
                .andExpect(jsonPath("$.type").value(request.getType()))
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andExpect(jsonPath("$.text").value(request.getText()))
                .andExpect(jsonPath("$.answer").value(request.getAnswer()))
                .andExpect(jsonPath("$.imgUrl").value(request.getImgUrl()));
    }

    @Test
    @Order(3)
    void getActivityById_WrongId() throws Exception {
        mockMvc.perform(get("/activity/{id}", Integer.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    void getAllActivities() throws Exception {
        mockMvc.perform(get("/activity")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    @Order(5)
    void updateActivity() throws Exception {
        ActivityRequest newRequest = new ActivityRequest();
        newRequest.setText("New text");

        mockMvc.perform(put("/activity/{id}", currentId)
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
    @Order(6)
    void deleteActivity() throws Exception {
        mockMvc.perform(delete("/activity/{id}", currentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/activity/{id}", currentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    void deleteActivity_NotFound() throws Exception {
        mockMvc.perform(delete("/activity/{id}", Integer.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}