package com.erstedigital.meetingappbackend.rest.service.impl;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Activity;
import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import com.erstedigital.meetingappbackend.persistence.data.User;
import com.erstedigital.meetingappbackend.persistence.repository.MeetingRepository;
import com.erstedigital.meetingappbackend.rest.data.request.AttendanceRequest;
import com.erstedigital.meetingappbackend.rest.data.request.MeetingRequest;
import com.erstedigital.meetingappbackend.rest.data.request.StatAttendanceRequest;
import com.erstedigital.meetingappbackend.rest.service.ActivityService;
import com.erstedigital.meetingappbackend.rest.service.AttendanceService;
import com.erstedigital.meetingappbackend.rest.service.MeetingService;
import com.erstedigital.meetingappbackend.rest.service.UserService;
import com.erstedigital.meetingappbackend.websockets.model.MeetingMessage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;
    private final ActivityService activityService;
    private final UserService userService;
    private final AttendanceService attendanceService;

    public MeetingServiceImpl(MeetingRepository meetingRepository, ActivityService activityService,
                              UserService userService, @Lazy AttendanceService attendanceService) {
        this.meetingRepository = meetingRepository;
        this.activityService = activityService;
        this.userService = userService;
        this.attendanceService = attendanceService;
    }

    @Override
    public List<Meeting> getAll() {
        return meetingRepository.findAll();
    }

    @Override
    public List<Meeting> getAll(Integer userId) throws NotFoundException {
        List<Meeting> meetings = meetingRepository.findAll();
        User user = userService.findById(userId);

        List<Meeting> userMeetings = meetings
                .stream()
                .filter(meeting -> Objects.equals(meeting.getOrganizer().getId(), userId))
                .collect(Collectors.toList());

        userMeetings.addAll(
                meetings
                        .stream()
                        .filter(meeting -> meeting.getAttendances().contains(user))
                        .collect(Collectors.toList())
        );

        return userMeetings;
    }

    @Override
    public Meeting findById(Integer id) throws NotFoundException {
        Optional<Meeting> meeting = meetingRepository.findById(id);
        if(meeting.isPresent()) {
            return meeting.get();
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public Meeting findByExchangeId(String id) throws NotFoundException {
        Optional<Meeting> meeting = meetingRepository.findByExchangeId(id);
        if(meeting.isPresent()) {
            return meeting.get();
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public Meeting create(MeetingRequest request) throws NotFoundException {
        Meeting meeting  = meetingRepository.save( new Meeting(
                request,
                userService.findById(request.getOrganizerId()),
                activityService.findById(request.getActivities())
        ));
        request.getAttendees().add(request.getOrganizerId());
        createAttendanceForMeeting(meeting.getId(), request.getAttendees());
        return meeting;
    }

    @Override
    public Integer startActivity(Integer activityId, Integer meetingId) throws NotFoundException {
        Meeting meeting = findById(meetingId);

        if (activityId != null && activityId > 0) {
            Activity activity = activityService.findById(activityId);
            meeting.setRunningActivity(activity);
            meetingRepository.save(meeting);
            return activityId;
        } else {
            meeting.setRunningActivity(null);
            meetingRepository.save(meeting);
            return -1;
        }
    }

    @Override
    public Meeting update(Integer id, MeetingRequest request) throws NotFoundException {
        Meeting meeting = findById(id);
        if(request.getExchangeId() != null) {
            meeting.setExchangeId(request.getExchangeId());
        }
        if(request.getSubject() != null) {
            meeting.setSubject(request.getSubject());
        }
        if(request.getDescription() != null) {
            meeting.setDescription(request.getDescription());
        }
        if(request.getMeetingType() != null) {
            meeting.setMeetingType(request.getMeetingType());
        }
        if(request.getFeedbackType() != null) {
            meeting.setFeedbackType(request.getFeedbackType());
        }
        if(request.getApolloCode() != null) {
            meeting.setApolloCode(request.getApolloCode());
        }
        if(request.getStart() != null) {
            meeting.setStart(request.getStart());
        }
        if(request.getActualStart() != null) {
            meeting.setActualStart(request.getActualStart());
        }
        if(request.getEnd() != null) {
            meeting.setEnd(request.getEnd());
        }
        if(request.getActualEnd() != null) {
            meeting.setActualEnd(request.getActualEnd());
        }
        if(request.getMeetingCost() != null) {
            meeting.setMeetingCost(request.getMeetingCost());
        }
        if(request.getNotesUrl() != null) {
            meeting.setNotesUrl(request.getNotesUrl());
        }
        if(request.getOrganizerId() != null) {
            meeting.setOrganizer(userService.findById(request.getOrganizerId()));
        }
        if(request.getActivities() != null) {
            meeting.setActivities(activityService.findById(request.getActivities()));
        }
        if(request.getLocation() != null) {
            meeting.setLocation(request.getLocation());
        }
        if(request.getLatitude() != null) {
            meeting.setLatitude(request.getLatitude());
        }
        if(request.getLongitude() != null) {
            meeting.setLongitude(request.getLongitude());
        }
        if(request.getUrl() != null) {
            meeting.setUrl(request.getUrl());
        }
        if(request.getApolloCode() != null) {
            meeting.setApolloCode(request.getApolloCode());
        }

        return meetingRepository.save(meeting);
     }

     @Override
    public Meeting update(Integer id, MeetingMessage request) throws NotFoundException {
         Meeting meeting = findById(id);
         if(request.getActualStart() != null) {
             meeting.setActualStart(request.getActualStart());
         }
         if(request.getActualEnd() != null) {
             meeting.setActualEnd(request.getActualEnd());
         }
         return meetingRepository.save(meeting);
    }


    @Override
    public void delete(Integer id) throws NotFoundException {
        meetingRepository.delete(findById(id));
    }

    @Override
    public void createAttendanceForMeeting(Integer meetingId, Set<Integer> attendees) throws NotFoundException {
        for (Integer userId : attendees) {
            AttendanceRequest request = new AttendanceRequest();
            request.setMeetingId(meetingId);
            request.setUserId(userId);
            attendanceService.create(request);
        }
    }

    @Override
    public List<Meeting> getOrganizerMeetings(Integer userId, Date start, Date end) throws NotFoundException {
        /*return meetingRepository.findByStartBetweenAndOrganizerLike(request.getStart(), request.getEnd(),
                userService.findById(request.getUserId()));*/
        return meetingRepository.getOrganizerMeetings(start, end, userId);
    }

    @Override
    public List<Meeting> getAttendeeMeetings(Integer userId, Date start, Date end) {
        return meetingRepository.getAttendeeMeetings(start, end, userId);
    }

}
