package com.erstedigital.meetingappbackend.rest.service;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import com.erstedigital.meetingappbackend.rest.data.request.MeetingRequest;
import com.erstedigital.meetingappbackend.websockets.model.MeetingMessage;
import com.erstedigital.meetingappbackend.rest.data.request.StatAttendanceRequest;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Date;
import java.util.List;
import java.util.Set;
public interface MeetingService {
    List<Meeting> getAll();

    List<Meeting> getAll(Integer userId) throws NotFoundException;

    Meeting findById(Integer id) throws NotFoundException;

    Meeting findByExchangeId(String id) throws NotFoundException;

    Meeting create(MeetingRequest request) throws NotFoundException;

    Integer startActivity(Integer activityId, Integer meetingId) throws NotFoundException;

    Meeting update(Integer id, MeetingRequest request) throws NotFoundException;

    Meeting update(Integer id, MeetingMessage request) throws NotFoundException;

    void delete(Integer id) throws NotFoundException;

    void createAttendanceForMeeting(Integer id, Set<Integer> attendees) throws NotFoundException;

    List<Meeting> getOrganizerMeetings(Integer userId, Date start, Date end) throws NotFoundException;

    List<Meeting> getAttendeeMeetings(Integer userId, Date start, Date end);

}
