package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.persistence.data.Meeting;
import com.erstedigital.meetingappbackend.persistence.repository.ActivityRepository;
import com.erstedigital.meetingappbackend.persistence.repository.MeetingRepository;
import com.erstedigital.meetingappbackend.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.Date;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping(path="/meetings")
public class MeetingController {
    private MeetingRepository meetingRepository;
    private UserRepository userRepository;
    private ActivityRepository activityRepository;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    @GetMapping(value = "/findById")
    public @ResponseBody
    Optional<Meeting> getMeetingById(@RequestParam(value = "id") Integer id) {
        return meetingRepository.findById(id);
    }

    @PostMapping(path="/add")
    public @ResponseBody
    String addNewMeeting(@RequestParam String meeting_type,
                         @RequestParam Date start_date,
                         @RequestParam Time start_time,
                         @RequestParam Time duration,
                         @RequestParam Optional<Time> actual_duration,
                         @RequestParam Optional<Integer> meeting_cost,
                         @RequestParam Optional<String> notes_url,
                         @RequestParam Integer organizer_id,
                         @RequestParam Optional<Integer> activity_id,
                         @RequestParam Optional<String> url) {
        Meeting meeting = new Meeting();
        meeting.setMeeting_type(meeting_type);
        meeting.setStart_date(start_date);
        meeting.setStart_time(start_time);
        meeting.setDuration(duration);
        actual_duration.ifPresent(meeting::setActual_duration);
        meeting_cost.ifPresent(meeting::setMeeting_cost);
        notes_url.ifPresent(meeting::setNotes_url);
        meeting.setOrganizer(userRepository.getById(organizer_id));
        activity_id.ifPresent(integer -> meeting.setActivity_id(activityRepository.getById(integer)));
        url.ifPresent(meeting::setUrl);

        meetingRepository.save(meeting);
        return "Saved";
    }

    @PutMapping(path="/update")
    public @ResponseBody String updateMeeting(@RequestParam Integer id,
                                              @RequestParam Optional<String> meeting_type,
                                              @RequestParam Optional<Date> start_date,
                                              @RequestParam Optional<Time> start_time,
                                              @RequestParam Optional<Time> duration,
                                              @RequestParam Optional<Time> actual_duration,
                                              @RequestParam Optional<Integer> meeting_cost,
                                              @RequestParam Optional<String> notes_url,
                                              @RequestParam Optional<Integer> organizer_id,
                                              @RequestParam Optional<Integer> activity_id,
                                              @RequestParam Optional<String> url) {
        Meeting meeting = meetingRepository.getById(id);
        meeting_type.ifPresent(meeting::setMeeting_type);
        start_date.ifPresent(meeting::setStart_date);
        start_time.ifPresent(meeting::setStart_time);
        duration.ifPresent(meeting::setDuration);
        actual_duration.ifPresent(meeting::setActual_duration);
        meeting_cost.ifPresent(meeting::setMeeting_cost);
        notes_url.ifPresent(meeting::setNotes_url);
        organizer_id.ifPresent(integer -> meeting.setOrganizer(userRepository.getById(integer)));
        activity_id.ifPresent(integer -> meeting.setActivity_id(activityRepository.getById(integer)));
        url.ifPresent(meeting::setUrl);

        meetingRepository.save(meeting);
        return "Updated";
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody String deleteMeeting(@RequestParam Integer id) {
        if(getMeetingById(id).isPresent()) {
            meetingRepository.delete(getMeetingById(id).get());
        }

        return "Deleted";
    }

    @Autowired
    public void setMeetingRepository(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private void setActivityRepository(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
}
