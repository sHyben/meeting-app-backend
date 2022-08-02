package com.erstedigital.meetingappbackend.persistence.repository;

import com.erstedigital.meetingappbackend.persistence.data.Note;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class NoteRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    NoteRepository repository;

    @Test
    void saveNote() {
        Note note = new Note();
        note.setText("Test");
        note = entityManager.persistAndFlush(note);
        assertThat(repository.findById(note.getId()).get()).isEqualTo(note);
    }

    // TODO
    @Test
    void findAllByMeetingId() {
    }

    @Test
    void deleteNote() {
        Note note = new Note();
        note.setText("Test");
        note = entityManager.persistAndFlush(note);
        repository.delete(note);
        assertThat(repository.findById(note.getId()).isPresent()).isEqualTo(false);
    }
}