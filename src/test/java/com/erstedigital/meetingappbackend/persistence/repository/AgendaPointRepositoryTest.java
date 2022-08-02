package com.erstedigital.meetingappbackend.persistence.repository;

import com.erstedigital.meetingappbackend.persistence.data.Agenda;
import com.erstedigital.meetingappbackend.persistence.data.AgendaPoint;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class AgendaPointRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AgendaRepository agendaRepository;

    @Autowired
    AgendaPointRepository repository;

    @Test
    void saveAgendaPoint() {
        AgendaPoint agendaPoint = new AgendaPoint();
        agendaPoint = entityManager.persistAndFlush(agendaPoint);
        assertThat(repository.findById(agendaPoint.getId()).get()).isEqualTo(agendaPoint);
    }

    @Test
    void findAllByAgendaId() {
        Agenda agenda = new Agenda();
        agenda = entityManager.persistAndFlush(agenda);

        AgendaPoint agendaPoint1 = new AgendaPoint();
        agendaPoint1.setAgenda(agenda);
        agendaPoint1 = entityManager.persistAndFlush(agendaPoint1);

        AgendaPoint agendaPoint2 = new AgendaPoint();
        agendaPoint2.setAgenda(agenda);
        agendaPoint2 = entityManager.persistAndFlush(agendaPoint2);

        List<AgendaPoint> list = repository.findAllByAgendaId(agenda.getId());
        assertThat(list.get(0)).isEqualTo(agendaPoint1);
        assertThat(list.get(1)).isEqualTo(agendaPoint2);
    }

    @Test
    void deleteAgendaPoint() {
        AgendaPoint agendaPoint = new AgendaPoint();
        agendaPoint = entityManager.persistAndFlush(agendaPoint);
        repository.delete(agendaPoint);
        assertThat(repository.findById(agendaPoint.getId()).isPresent()).isEqualTo(false);
    }
}