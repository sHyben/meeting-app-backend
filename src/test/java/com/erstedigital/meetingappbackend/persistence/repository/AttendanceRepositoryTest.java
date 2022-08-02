package com.erstedigital.meetingappbackend.persistence.repository;

import com.erstedigital.meetingappbackend.persistence.data.Attendance;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class AttendanceRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AttendanceRepository repository;

    @Test
    void saveAttendance() {
        Attendance attendance = new Attendance();
        attendance.setParticipation(true);
        attendance = entityManager.persistAndFlush(attendance);
        assertThat(repository.findById(attendance.getId()).get()).isEqualTo(attendance);
    }

    // TODO
    @Test
    void findByAttendanceMeeting() {
    }

    @Test
    void deleteAttendance() {
        Attendance attendance = new Attendance();
        attendance.setParticipation(true);
        attendance = entityManager.persistAndFlush(attendance);
        repository.delete(attendance);
        assertThat(repository.findById(attendance.getId()).isPresent()).isEqualTo(false);
    }
}