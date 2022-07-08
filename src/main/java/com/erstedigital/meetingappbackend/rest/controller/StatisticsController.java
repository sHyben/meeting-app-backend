package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.rest.data.Statistics;
import com.erstedigital.meetingappbackend.rest.data.request.StatAttendanceRequest;
import com.erstedigital.meetingappbackend.rest.service.StatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;



@CrossOrigin(origins = {"https://www.bettermeetings.sk","http://localhost:3000"}, maxAge = 3600)
@RestController
@RequestMapping(path="/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping(value = "/organizer")
    public @ResponseBody
    Statistics getOrganizerStatistics(@RequestParam Integer userId,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) throws NotFoundException {
        return statisticsService.getOrganizerStatistics(userId, start, end);
    }

    @GetMapping(value = "/attendee")
    public @ResponseBody
    Statistics getAttendeeStatistics(@RequestParam Integer userId, @RequestParam Date start, @RequestParam Date end) throws NotFoundException {
        return statisticsService.getAttendeeStatistics(userId, start, end);
    }

    @GetMapping(value = "/days")
    public @ResponseBody
    List<LocalDate> getDays(@RequestBody StatAttendanceRequest body) {
        return statisticsService.returnDays(body);
    }
}

