package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.rest.data.request.ActivityRequest;
import com.erstedigital.meetingappbackend.rest.data.response.ActivityResponse;
import com.erstedigital.meetingappbackend.rest.service.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "https://www.bettermeetings.sk", maxAge = 3600)
@RestController
@RequestMapping(path="/activity")
public class ActivityController {
    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public @ResponseBody
    List<ActivityResponse> getAllActivities() {
        return activityService.getAll().stream().map(ActivityResponse::new).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    ActivityResponse getActivityById(@PathVariable("id") Integer id) throws NotFoundException {
        return new ActivityResponse(activityService.findById(id));
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<ActivityResponse> addNewActivity(@RequestBody ActivityRequest body) {
        return new ResponseEntity<>(new ActivityResponse(activityService.create(body)), HttpStatus.CREATED);
    }

    @PutMapping(path="/{id}")
    public @ResponseBody ActivityResponse updateActivity(@PathVariable("id") Integer id,
                                                         @RequestBody ActivityRequest body) throws NotFoundException {
        return new ActivityResponse(activityService.update(id, body));
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody void deleteActivity(@PathVariable("id") Integer id) throws NotFoundException {
        activityService.delete(id);
    }
}

