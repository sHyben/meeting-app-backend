package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.persistence.data.Attendee;
import com.erstedigital.meetingappbackend.persistence.repository.AttendeeRepository;
import com.erstedigital.meetingappbackend.persistence.repository.MeetingRepository;
import com.erstedigital.meetingappbackend.persistence.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping(path="/attendees")
public class AttendeeController {
    private AttendeeRepository attendeeRepository;
    private MeetingRepository meetingRepository;
    private PositionRepository positionRepository;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Attendee> getAllAttendees() {
        return attendeeRepository.findAll();
    }

    @GetMapping(value = "/findById")
    public @ResponseBody
    Optional<Attendee> getAttendeeById(@RequestParam(value = "id") Integer id) {
        return attendeeRepository.findById(id);
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewAttendee(@RequestParam String email,
                                               @RequestParam Optional<Integer> feedback_rating,
                                               @RequestParam Optional<String> feedback_comment,
                                               @RequestParam Optional<String> participation,
                                               @RequestParam Integer attendee_meeting,
                                               @RequestParam Optional<Integer> attendee_position) {
        Attendee attendee = new Attendee();
        attendee.setEmail(email);
        feedback_rating.ifPresent(integer -> attendee.setFeedback_rating(feedback_rating.get()));
        feedback_comment.ifPresent(string -> attendee.setFeedback_comment(feedback_comment.get()));
        participation.ifPresent(string -> attendee.setParticipation(participation.get()));
        attendee.setAttendee_meeting(meetingRepository.getById(attendee_meeting));
        attendee_position.ifPresent(integer -> attendee.setAttendee_position(positionRepository.getById(integer)));

        attendeeRepository.save(attendee);
        return "Saved";
    }

    @PutMapping(path="/update")
    public @ResponseBody String updateAttendee(@RequestParam Integer id,
                                               @RequestParam Optional<String> email,
                                               @RequestParam Optional<Integer> feedback_rating,
                                               @RequestParam Optional<String> feedback_comment,
                                               @RequestParam Optional<String> participation,
                                               @RequestParam Optional<Integer> attendee_meeting,
                                               @RequestParam Optional<Integer> attendee_position) {
        Attendee attendee = attendeeRepository.getById(id);
        email.ifPresent(attendee::setEmail);
        feedback_rating.ifPresent(attendee::setFeedback_rating);
        feedback_comment.ifPresent(attendee::setFeedback_comment);
        participation.ifPresent(attendee::setParticipation);
        attendee_meeting.ifPresent(integer -> attendee.setAttendee_meeting(meetingRepository.getById(integer)));
        attendee_position.ifPresent(integer -> attendee.setAttendee_position(positionRepository.getById(integer)));

        attendeeRepository.save(attendee);
        return "Updated";
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody String deleteAttendee(@RequestParam Integer id) {
        if(getAttendeeById(id).isPresent()) {
            attendeeRepository.delete(getAttendeeById(id).get());
        }

        return "Deleted";
    }

    @Autowired
    private void setAttendeeRepository(AttendeeRepository attendeeRepository) {
        this.attendeeRepository = attendeeRepository;
    }

    @Autowired
    public void setMeetingRepository(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Autowired
    private void setPositionRepository(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }
}

