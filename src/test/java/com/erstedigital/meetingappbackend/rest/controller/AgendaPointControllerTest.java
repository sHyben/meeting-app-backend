package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.rest.data.request.*;
import com.erstedigital.meetingappbackend.rest.service.AgendaPointService;
import com.erstedigital.meetingappbackend.rest.service.AgendaService;
import com.erstedigital.meetingappbackend.rest.service.PositionService;
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
import java.util.HashSet;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    private static AgendaPointRequest request;
    private static Integer currentId;
    private static Integer currentAgendaId;

    @BeforeAll
    public static void setUpRequest() {
        request = new AgendaPointRequest();
        request.setTitle("Test title");
    }

    @Test
    @Order(1)
    void addNewAgendaPoint() throws Exception {
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

        AgendaRequest agendaRequest = new AgendaRequest();
        agendaRequest.setMeetingId(meetingId);
        MvcResult result4 = mockMvc.perform(post("/agenda")
                        .content(mapper.writeValueAsString(agendaRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        String response4 = result4.getResponse().getContentAsString();
        currentAgendaId = JsonPath.parse(response4).read("id");

        request.setAgendaId(currentAgendaId);
        MvcResult result = mockMvc.perform(post("/agendaPoint")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.agendaId").value(request.getAgendaId()))
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        currentId = JsonPath.parse(response).read("id");
    }

    @Test
    @Order(2)
    void addNewAgendaPoints() throws Exception {
        AgendaPointRequest newRequest = new AgendaPointRequest();
        newRequest.setTitle("New test title");
        newRequest.setAgendaId(request.getAgendaId());
        List<AgendaPointRequest> list = Arrays.asList(request, newRequest);

        mockMvc.perform(post("/agendaPoint/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(mapper.writeValueAsString(list)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    @Order(3)
    void getAgendaPointById() throws Exception {
        mockMvc.perform(get("/agendaPoint/{id}", currentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(currentId))
                .andExpect(jsonPath("$.agendaId").value(request.getAgendaId()));
    }

    @Test
    @Order(4)
    void getAgendaPointById_WrongId() throws Exception {
        mockMvc.perform(get("/agendaPoint/{id}", Integer.MAX_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    void getAllAgendaPoints() throws Exception {
        mockMvc.perform(get("/agendaPoint")
                        .param("agendaId", currentAgendaId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    @Order(6)
    void updateAgendaPoint() throws Exception {
        AgendaPointRequest newRequest = new AgendaPointRequest();
        newRequest.setTitle("New test title");

        mockMvc.perform(put("/agendaPoint/{id}", currentId)
                        .content(mapper.writeValueAsString(newRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New test title"));
    }

    @Test
    @Order(7)
    void deleteAgendaPoint() throws Exception {
        mockMvc.perform(delete("/agendaPoint/{id}", currentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/agendaPoint/{id}", currentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}