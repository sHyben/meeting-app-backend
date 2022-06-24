package com.erstedigital.meetingappbackend.persistence.repository;

import com.erstedigital.meetingappbackend.persistence.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByName(String name);

    Optional<User> findByEmail(String email);

    @Query("select u from User u join Attendance a on u.id = a.attendanceUser.id where a.attendanceMeeting.id = :meetingId")
    List<User> findMeetingAttendees(@Param("meetingId") Integer meetingId);
}