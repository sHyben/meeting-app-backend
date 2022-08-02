package com.erstedigital.meetingappbackend.persistence.repository;

import com.erstedigital.meetingappbackend.persistence.data.Activity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class ActivityRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ActivityRepository repository;

    @Test
    void saveUser() {
        Activity activity = new Activity();
        activity = entityManager.persistAndFlush(activity);
        assertThat(repository.findById(activity.getId()).get()).isEqualTo(activity);
    }

    @Test
    void deleteUser() {
        Activity activity = new Activity();
        activity = entityManager.persistAndFlush(activity);
        repository.delete(activity);
        assertThat(repository.findById(activity.getId()).isPresent()).isEqualTo(false);
    }
}