package com.erstedigital.meetingappbackend.persistence.repository;

import com.erstedigital.meetingappbackend.persistence.data.Position;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class PositionRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PositionRepository repository;

    @Test
    void savePosition() {
        Position position = new Position();
        position.setName("Test Position");
        position = entityManager.persistAndFlush(position);
        assertThat(repository.findById(position.getId()).get()).isEqualTo(position);
    }

    @Test
    void deletePosition() {
        Position position = new Position();
        position.setName("Test Position");
        position = entityManager.persistAndFlush(position);
        repository.delete(position);
        assertThat(repository.findById(position.getId()).isPresent()).isEqualTo(false);
    }
}