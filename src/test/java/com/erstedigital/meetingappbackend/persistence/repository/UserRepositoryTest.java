package com.erstedigital.meetingappbackend.persistence.repository;

import com.erstedigital.meetingappbackend.persistence.data.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UserRepository repository;

    @Test
    void saveUser() {
        User user = new User();
        user.setName("Test User");
        user = entityManager.persistAndFlush(user);
        assertThat(repository.findById(user.getId()).get()).isEqualTo(user);
    }

    @Test
    void findByName() {
        User user = new User();
        user.setName("Test User");
        List<User> list = new ArrayList<>();
        list.add(user);
        user = entityManager.persistAndFlush(user);
        assertThat(repository.findByName(user.getName())).isEqualTo(list);
    }

    @Test
    void findByEmail() {
        User user = new User();
        user.setEmail("test@test.com");
        user = entityManager.persistAndFlush(user);
        assertThat(repository.findByEmail(user.getEmail()).get()).isEqualTo(user);
    }

    @Test
    void deleteUser() {
        User user = new User();
        user.setName("Test User");
        user = entityManager.persistAndFlush(user);
        repository.delete(user);
        assertThat(repository.findById(user.getId()).isPresent()).isEqualTo(false);
    }
}