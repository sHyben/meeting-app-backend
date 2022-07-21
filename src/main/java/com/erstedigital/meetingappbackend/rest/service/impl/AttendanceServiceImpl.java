package com.erstedigital.meetingappbackend.rest.service.impl;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Attendance;
import com.erstedigital.meetingappbackend.persistence.repository.AttendanceRepository;
import com.erstedigital.meetingappbackend.rest.data.request.AttendanceRequest;
import com.erstedigital.meetingappbackend.rest.service.AttendanceService;
import com.erstedigital.meetingappbackend.rest.service.MeetingService;
import com.erstedigital.meetingappbackend.rest.service.UserService;
import com.erstedigital.meetingappbackend.websockets.model.AttendanceMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final MeetingService meetingService;
    private final UserService userService;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, MeetingService meetingService, UserService userService) {
        this.attendanceRepository = attendanceRepository;
        this.meetingService = meetingService;
        this.userService = userService;
    }

    @Override
    public List<Attendance> getAll() {
        return attendanceRepository.findAll();
    }

    @Override
    public Attendance findById(Integer id) throws NotFoundException {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        if(attendance.isPresent()) {
            return attendance.get();
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public Attendance create(AttendanceRequest request) throws NotFoundException {
        return attendanceRepository.save(new Attendance(request, meetingService.findById(request.getMeetingId()), userService.findById(request.getUserId())));
    }

    @Override
    public Attendance update(Integer id, AttendanceRequest request) throws NotFoundException {
        Attendance attendance = findById(id);
        if(request.getFeedbackRating() != null) {
            attendance.setFeedbackRating(request.getFeedbackRating());
        }
        if(request.getFeedbackComment() != null) {
            attendance.setFeedbackComment(request.getFeedbackComment());
        }
        if(request.isParticipation()) {
            attendance.setParticipation(request.isParticipation());
        }
        if(request.getMeetingId() != null) {
            attendance.setAttendanceMeeting(meetingService.findById(request.getMeetingId()));
        }
        if(request.getUserId() != null) {
            attendance.setAttendanceUser(userService.findById(request.getUserId()));
        }
        if(request.getPresenceTime() != null) {
            attendance.setPresenceTime(request.getPresenceTime());
        }
        if(request.getLastJoinedAt() != null) {
            attendance.setLastJoinedAt(request.getLastJoinedAt());
        }

        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance update(AttendanceMessage message) throws NotFoundException {
        Attendance attendance = findById(message.getId());
        if(message.getParticipation() != null) {
            attendance.setParticipation(message.getParticipation());
        }
        if(message.getPresentTime() != null) {
            attendance.setPresenceTime(message.getPresentTime());
        }
        if(message.getLastJoinedAt() != null) {
            attendance.setLastJoinedAt(message.getLastJoinedAt());
        }

        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance update(Attendance newAttendance){
        return attendanceRepository.save(newAttendance);
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        attendanceRepository.delete(findById(id));
    }

    @Override
    public List<Attendance> getMeetingAttendances(Integer id) throws NotFoundException {
        return attendanceRepository.findByAttendanceMeeting(meetingService.findById(id));
    }
}
