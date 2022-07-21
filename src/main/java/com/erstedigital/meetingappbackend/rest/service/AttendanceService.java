package com.erstedigital.meetingappbackend.rest.service;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Attendance;
import com.erstedigital.meetingappbackend.rest.data.request.AttendanceRequest;
import com.erstedigital.meetingappbackend.websockets.model.AttendanceMessage;
import java.util.List;

public interface AttendanceService {
    List<Attendance> getAll();

    Attendance findById(Integer id) throws NotFoundException;

    Attendance create(AttendanceRequest request) throws NotFoundException;

    Attendance update(Integer id, AttendanceRequest request) throws NotFoundException;

    Attendance update(AttendanceMessage message) throws NotFoundException;

    Attendance update(Attendance newAttendance);

    void delete(Integer id) throws NotFoundException;

    List<Attendance> getMeetingAttendances(Integer id) throws NotFoundException;
}
