package com.erstedigital.meetingappbackend.rest.service.impl;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Attendance;
import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import com.erstedigital.meetingappbackend.rest.data.Statistics;
import com.erstedigital.meetingappbackend.rest.data.request.StatAttendanceRequest;
import com.erstedigital.meetingappbackend.rest.service.AttendanceService;
import com.erstedigital.meetingappbackend.rest.service.MeetingService;
import com.erstedigital.meetingappbackend.rest.service.StatisticsService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final MeetingService meetingService;
    private final AttendanceService attendanceService;

    public StatisticsServiceImpl(MeetingService meetingService, AttendanceService attendanceService) {
        this.meetingService = meetingService;
        this.attendanceService = attendanceService;
    }

    @Override
    public Statistics getOrganizerStatistics(StatAttendanceRequest body) throws NotFoundException {
        List<Meeting> resultMeetings = meetingService.getOrganizerMeetings(body);
        return getStatistics(resultMeetings, body);
    }

    @Override
    public Statistics getAttendeeStatistics(StatAttendanceRequest body) throws NotFoundException {
        List<Meeting> resultMeetings = meetingService.getAttendeeMeetings(body);
        return getStatistics(resultMeetings, body);
    }

    private Statistics getStatistics(List<Meeting> resultMeetings, StatAttendanceRequest body) throws NotFoundException {
        Statistics resultStatistics = new Statistics();
        LocalDate startDate = convertToLocalDate(body.getStart());
        LocalDate endDate = convertToLocalDate(body.getEnd());
        List<LocalDate> days = getDays(startDate, endDate);
        Integer totalMeetings = resultMeetings.size();

        List<List<Attendance>> attendanceList = getAttendances(resultMeetings);

        List<Integer> attendanceNumbers = getAttendanceNumbers(attendanceList);

        resultStatistics.setTotalHours(attendanceNumbers.get(0));
        resultStatistics.setTotalMeetings(totalMeetings);
        resultStatistics.setTotalAttendees(attendanceNumbers.get(1));
        resultStatistics.setPositiveFeedback(attendanceNumbers.get(2));
        resultStatistics.setNegativeFeedback(attendanceNumbers.get(3));
        resultStatistics.setNeutralFeedback(attendanceNumbers.get(4));

        //TODO get statistics for days


        return resultStatistics;
    }

    private List<LocalDate> getDays(LocalDate startDate, LocalDate endDate) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(startDate::plusDays)
                .collect(Collectors.toList());
    }

    private List<List<Attendance>> getAttendances(List<Meeting> meetings) throws NotFoundException {
        List<List<Attendance>> result = new ArrayList<>();
        for (Meeting meeting: meetings) {
            List<Attendance> meetingAttendance = attendanceService.getMeetingAttendances(meeting.getId());
            result.add(meetingAttendance);
        }

        return result;
    }

    //Return list of totalHours, totalAttendees, positiveFeedback, negativeFeedback, neutralFeedback
    private List<Integer> getAttendanceNumbers(List<List<Attendance>> attendances) {
        List<Integer> result = new ArrayList<>();
        Integer totalHours = 0;
        Integer totalAttendees = 0;
        Integer positiveFeedback = 0;
        Integer negativeFeedback = 0;
        Integer neutralFeedback = 0;
        for (List<Attendance> attendanceList : attendances) {
            for(Attendance attendance : attendanceList) {
                if(attendance.getPresenceTime() == null) attendance.setPresenceTime(0);
                totalHours += attendance.getPresenceTime();
                totalAttendees += 1;
                Integer rating = attendance.getFeedbackRating();
                if(rating != null) {
                    if (rating > 3) {
                        positiveFeedback += 1;
                    } else if (rating == 3) {
                        neutralFeedback += 1;
                    } else {
                        negativeFeedback += 1;
                    }
                }
            }
        }

        result.add(totalHours);
        result.add(totalAttendees);
        result.add(positiveFeedback);
        result.add(negativeFeedback);
        result.add(neutralFeedback);

        return result;
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public List<LocalDate> returnDays(StatAttendanceRequest body) {
        return getDays(convertToLocalDate(body.getStart()), convertToLocalDate(body.getEnd()));
    }
}
