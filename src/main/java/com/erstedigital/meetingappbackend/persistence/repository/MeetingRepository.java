package com.erstedigital.meetingappbackend.persistence.repository;

import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import com.erstedigital.meetingappbackend.persistence.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    Optional<Meeting> findByExchangeId(String exchange_id);

    List<Meeting> findByStartBetweenAndOrganizerLike(Date start, Date end, User organizer);

    @Query("select m, a from Meeting m, Attendance a where m.organizer = :userId and a.attendanceMeeting.id = m.id and" +
            " a.attendanceUser.id = :userId and m.start > :start and m.end < :end")
    List<Object[]> getMeetingsInTimespan(@Param("start") Date start, @Param("end") Date end, @Param("userId") Integer userId);
}
