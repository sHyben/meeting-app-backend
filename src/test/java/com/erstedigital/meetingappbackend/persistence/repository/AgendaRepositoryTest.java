package com.erstedigital.meetingappbackend.persistence.repository;

import com.erstedigital.meetingappbackend.persistence.data.Agenda;
import com.erstedigital.meetingappbackend.persistence.data.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class AgendaRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AgendaRepository repository;

    @Test
    void saveAgenda() {
        Agenda agenda = new Agenda();
        agenda = entityManager.persistAndFlush(agenda);
        assertThat(repository.findById(agenda.getId()).get()).isEqualTo(agenda);
    }

    @Test
    void deleteAgenda() {
        Agenda agenda = new Agenda();
        agenda = entityManager.persistAndFlush(agenda);
        repository.delete(agenda);
        assertThat(repository.findById(agenda.getId()).isPresent()).isEqualTo(false);
    }
}