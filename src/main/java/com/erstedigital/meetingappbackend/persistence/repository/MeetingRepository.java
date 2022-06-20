package com.erstedigital.meetingappbackend.persistence.repository;

import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import com.erstedigital.meetingappbackend.persistence.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    Optional<Meeting> findByExchangeId(String exchange_id);

    List<Meeting> findByStartBetweenAndOrganizerLike(Date start, Date end, User organizer);
}
