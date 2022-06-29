package com.erstedigital.meetingappbackend.rest.service;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.rest.data.Statistics;
import com.erstedigital.meetingappbackend.rest.data.request.StatAttendanceRequest;

import java.time.LocalDate;
import java.util.List;

public interface StatisticsService {
    Statistics getOrganizerStatistics(StatAttendanceRequest body) throws NotFoundException;

    Statistics getAttendeeStatistics(StatAttendanceRequest body) throws NotFoundException;

    List<LocalDate> returnDays(StatAttendanceRequest body);
}

