package com.erstedigital.meetingappbackend.persistence.data;

import static org.assertj.core.api.Assertions.assertThat;

import com.erstedigital.meetingappbackend.rest.controller.ActivityController;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ActivityTest {

    @Autowired
    private ActivityController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void testEquals() {
        var entity = new Activity();
        entity.setId(1);
        entity.setType("type");
        entity.setTitle("title");
        entity.setText("text");
        entity.setAnswer("answer");
        entity.setImgUrl("url");
        assertThat(entity.equals(entity)).isTrue();

        var entity2 = new Activity();
        entity2.setId(2);
        entity2.setType("type");
        entity2.setTitle("title");
        entity2.setText("text");
        entity2.setAnswer("answer");
        entity2.setImgUrl("url");
        assertThat(entity.equals(entity2)).isFalse();
    }

    @Test
    void testHashCode() {
    }

    @Test
    void getId() {
        var entity = new Activity();
        entity.setId(1);
        assertThat(entity.getId()).isEqualByComparingTo(1);
    }

    @Test
    void getType() {
        var entity = new Activity();
        entity.setType("custom");
        assertThat(entity.getType()).isEqualTo("custom");
    }

    @Test
    void getTitle() {
        var entity = new Activity();
        entity.setTitle("title");
        assertThat(entity.getTitle()).isEqualTo("title");
    }

    @Test
    void getText() {
        var entity = new Activity();
        entity.setText("text");
        assertThat(entity.getText()).isEqualTo("text");
    }

    @Test
    void getAnswer() {
        var entity = new Activity();
        entity.setAnswer("answer");
        assertThat(entity.getAnswer()).isEqualTo("answer");
    }

    @Test
    void getImgUrl() {
        var entity = new Activity();
        entity.setImgUrl("url");
        assertThat(entity.getImgUrl()).isEqualTo("url");
    }

    @Test
    void testToString() {
        var entity = new Activity();
        entity.setId(1);
        entity.setType("type");
        entity.setTitle("title");
        entity.setText("text");
        entity.setAnswer("answer");
        entity.setImgUrl("url");
        assertThat(entity.toString()).isEqualTo("Activity(id=1, type=type, title=title, text=text, answer=answer, imgUrl=url)");
    }
}