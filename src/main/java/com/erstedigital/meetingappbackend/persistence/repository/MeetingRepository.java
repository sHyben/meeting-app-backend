package com.erstedigital.meetingappbackend.persistence.repository;

import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import com.erstedigital.meetingappbackend.persistence.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    Optional<Meeting> findByExchangeId(String exchange_id);

    List<Meeting> findByStartBetweenAndOrganizerLike(Date start, Date end, User organizer);

    @Query("select m from Meeting m where m.organizer.id = :userId and" +
            " m.start > :start and m.end < :end")
    List<Meeting> getOrganizerMeetings(@Param("start") Date start, @Param("end") Date end, @Param("userId") Integer userId);

    @Query("select m from Meeting m, Attendance a where m.id = a.attendanceMeeting.id and a.attendanceUser.id = :userId and" +
            " m.start > :start and m.end < :end")
    List<Meeting> getAttendeeMeetings(@Param("start") Date start, @Param("end") Date end, @Param("userId") Integer userId);
}
