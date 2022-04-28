package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.persistence.data.Activity;
import com.erstedigital.meetingappbackend.persistence.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping(path="/activities")
public class ActivityController {
    private ActivityRepository activityRepository;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @GetMapping(value = "/findById")
    public @ResponseBody
    Optional<Activity> getActivityById(@RequestParam(value = "id") Integer id) {
        return activityRepository.findById(id);
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewActivity(@RequestParam String type, @RequestParam String title,
                                               @RequestParam String text, @RequestParam String answer,
                                               @RequestParam String img_url) {
        Activity activity = new Activity();
        activity.setType(type);
        activity.setTitle(title);
        activity.setText(text);
        activity.setAnswer(answer);
        activity.setImg_url(img_url);
        activityRepository.save(activity);
        return "Saved";
    }

    @PutMapping(path="/update")
    public @ResponseBody String updateActivity(@RequestParam Integer id, @RequestParam Optional<String> type,
                                               @RequestParam Optional<String> title, @RequestParam Optional<String> text,
                                               @RequestParam Optional<String> answer, @RequestParam Optional<String> img_url) {
        Activity activity = activityRepository.getById(id);
        type.ifPresent(activity::setType);
        title.ifPresent(activity::setTitle);
        text.ifPresent(activity::setText);
        answer.ifPresent(activity::setAnswer);
        img_url.ifPresent(activity::setImg_url);

        activityRepository.save(activity);
        return "Updated";
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody String deleteActivity (@RequestParam Integer id) {
        if(getActivityById(id).isPresent()) {
            activityRepository.delete(getActivityById(id).get());
        }

        return "Deleted";
    }

    @Autowired
    private void setActivityRepository(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
}

