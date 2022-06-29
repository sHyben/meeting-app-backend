package com.erstedigital.meetingappbackend.persistence.repository;

import com.erstedigital.meetingappbackend.persistence.data.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> findAllByMeetingId(Integer meetingId);
}
