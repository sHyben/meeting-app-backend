package com.erstedigital.meetingappbackend.rest.service.impl;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Attendee;
import com.erstedigital.meetingappbackend.persistence.repository.AttendeeRepository;
import com.erstedigital.meetingappbackend.rest.data.request.AttendeeRequest;
import com.erstedigital.meetingappbackend.rest.service.AttendeeService;
import com.erstedigital.meetingappbackend.rest.service.MeetingService;
import com.erstedigital.meetingappbackend.rest.service.PositionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendeeServiceImpl implements AttendeeService {
    private final AttendeeRepository attendeeRepository;
    private final MeetingService meetingService;
    private final PositionService positionService;

    public AttendeeServiceImpl(AttendeeRepository attendeeRepository, MeetingService meetingService, PositionService positionService) {
        this.attendeeRepository = attendeeRepository;
        this.meetingService = meetingService;
        this.positionService = positionService;
    }

    @Override
    public List<Attendee> getAll() {
        return attendeeRepository.findAll();
    }

    @Override
    public Attendee findById(Integer id) throws NotFoundException {
        Optional<Attendee> attendee = attendeeRepository.findById(id);
        if(attendee.isPresent()) {
            return attendee.get();
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public Attendee create(AttendeeRequest request) throws NotFoundException {
        return attendeeRepository.save(new Attendee(request, meetingService.findById(request.getMeetingId()), positionService.findById(request.getPositionId())));
    }

    @Override
    public Attendee update(Integer id, AttendeeRequest request) throws NotFoundException {
        Attendee attendee = findById(id);
        if(request.getEmail() != null) {
            attendee.setEmail(request.getEmail());
        }
        if(request.getFeedbackRating() != null) {
            attendee.setFeedback_rating(request.getFeedbackRating());
        }
        if(request.getFeedbackComment() != null) {
            attendee.setFeedback_comment(request.getFeedbackComment());
        }
        if(request.getParticipation() != null) {
            attendee.setParticipation(request.getParticipation());
        }
        if(request.getMeetingId() != null) {
            attendee.setAttendee_meeting(meetingService.findById(request.getMeetingId()));
        }
        if(request.getPositionId() != null) {
            attendee.setAttendee_position(positionService.findById(request.getPositionId()));
        }
        return attendeeRepository.save(attendee);
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        attendeeRepository.delete(findById(id));
    }
}
