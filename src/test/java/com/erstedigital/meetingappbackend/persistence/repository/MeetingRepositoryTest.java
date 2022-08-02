package com.erstedigital.meetingappbackend.persistence.repository;

import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class MeetingRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    MeetingRepository repository;

    @Test
    void saveUser() {
        Meeting meeting = new Meeting();
        meeting.setSubject("Test Subject");
        meeting = entityManager.persistAndFlush(meeting);
        assertThat(repository.findById(meeting.getId()).get()).isEqualTo(meeting);
    }

    @Test
    void findByExchangeId() {
        Meeting meeting = new Meeting();
        meeting.setExchangeId("qwertyuiop123456");
        meeting = entityManager.persistAndFlush(meeting);
        assertThat(repository.findByExchangeId(meeting.getExchangeId()).get()).isEqualTo(meeting);
    }

    // TODO
    @Test
    void getOrganizerMeetings() {
    }

    @Test
    void getAttendeeMeetings() {
    }

    @Test
    void deleteMeeting() {
        Meeting meeting = new Meeting();
        meeting.setSubject("Test Subject");
        meeting = entityManager.persistAndFlush(meeting);
        repository.delete(meeting);
        assertThat(repository.findById(meeting.getId()).isPresent()).isEqualTo(false);
    }
}