package com.erstedigital.meetingappbackend.rest.service.impl;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import com.erstedigital.meetingappbackend.persistence.repository.MeetingRepository;
import com.erstedigital.meetingappbackend.rest.data.request.MeetingRequest;
import com.erstedigital.meetingappbackend.rest.service.ActivityService;
import com.erstedigital.meetingappbackend.rest.service.MeetingService;
import com.erstedigital.meetingappbackend.rest.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;
    private final ActivityService activityService;
    private final UserService userService;

    public MeetingServiceImpl(MeetingRepository meetingRepository, ActivityService activityService,
                              UserService userService) {
        this.meetingRepository = meetingRepository;
        this.activityService = activityService;
        this.userService = userService;
    }

    @Override
    public List<Meeting> getAll() {
        return meetingRepository.findAll();
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
    public Meeting create(MeetingRequest request) throws NotFoundException {
        return meetingRepository.save(new Meeting(request, userService.findById(request.getOrganizerId()),
                activityService.findById(request.getActivityId())));
    }

    @Override
    public Meeting update(Integer id, MeetingRequest request) throws NotFoundException {
        Meeting meeting = findById(id);
        if(request.getExchangeId() != null) {
            meeting.setExchange_id(request.getExchangeId());
        }
        if(request.getMeetingType() != null) {
            meeting.setMeeting_type(request.getMeetingType());
        }
        if(request.getStartDate() != null) {
            meeting.setStart_date(request.getStartDate());
        }
        if(request.getStartTime() != null) {
            meeting.setStart_time(request.getStartTime());
        }
        if(request.getDuration() != null) {
            meeting.setDuration(request.getDuration());
        }
        if(request.getActualDuration() != null) {
            meeting.setActual_duration(request.getActualDuration());
        }
        if(request.getMeetingCost() != null) {
            meeting.setMeeting_cost(request.getMeetingCost());
        }
        if(request.getNotesUrl() != null) {
            meeting.setNotes_url(request.getNotesUrl());
        }
        if(request.getOrganizerId() != null) {
            meeting.setOrganizer(userService.findById(request.getOrganizerId()));
        }
        if(request.getActivityId() != null) {
            meeting.setActivity_id(activityService.findById(request.getActivityId()));
        }
        if(request.getUrl() != null) {
            meeting.setUrl(request.getUrl());
        }
        return meetingRepository.save(meeting);
     }

    @Override
    public void delete(Integer id) throws NotFoundException {
        meetingRepository.delete(findById(id));
    }
}
