package com.erstedigital.meetingappbackend.persistence.repository;

import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    Optional<Meeting> findByExchangeId(String exchange_id);
}
