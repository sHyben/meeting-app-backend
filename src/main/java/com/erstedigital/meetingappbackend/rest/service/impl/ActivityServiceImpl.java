package com.erstedigital.meetingappbackend.rest.service.impl;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Activity;
import com.erstedigital.meetingappbackend.persistence.repository.ActivityRepository;
import com.erstedigital.meetingappbackend.rest.data.request.ActivityRequest;
import com.erstedigital.meetingappbackend.rest.service.ActivityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public List<Activity> getAll() {
        return activityRepository.findAll();
    }

    @Override
    public Activity findById(Integer id) throws NotFoundException {
        Optional<Activity> activity = activityRepository.findById(id);
        if(activity.isPresent()) {
            return activity.get();
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public Activity create(ActivityRequest request) {
        return this.activityRepository.save(new Activity(request));
    }

    @Override
    public Activity update(Integer id, ActivityRequest request) throws NotFoundException {
        Activity activity = findById(id);
        if (request.getType() != null) {
            activity.setType(request.getType());
        }
        if (request.getTitle() != null) {
            activity.setTitle(request.getTitle());
        }
        if (request.getText() != null) {
            activity.setText(request.getText());
        }
        if (request.getAnswer() != null) {
            activity.setAnswer(request.getAnswer());
        }
        if (request.getImgUrl() != null) {
            activity.setImgUrl(request.getImgUrl());
        }
        return activityRepository.save(activity);
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        activityRepository.delete(findById(id));
    }
}
