package com.erstedigital.meetingappbackend.rest.service;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Activity;
import com.erstedigital.meetingappbackend.persistence.repository.ActivityRepository;
import com.erstedigital.meetingappbackend.rest.data.request.ActivityRequest;
import com.erstedigital.meetingappbackend.rest.service.impl.ActivityServiceImpl;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class ActivityServiceTest {
    @SpyBean
    private ActivityServiceImpl service;

    @MockBean
    private ActivityRepository repository;

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
    void getAll() {
    }

    @Test
    void findById() throws NotFoundException {
/*        Activity activity = new Activity();

        var id = 1;
        var entity = new Activity();
        entity.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        Activity result = service.findById(id);*/
    }

    @Test
    void testFindById() {
    }

    @Test
    void create() throws NotFoundException {
        Activity activity = service.create(request);
        assertThat(service.findById(activity.getId())).isEqualTo(activity);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}