package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.rest.data.request.UserRequest;
import com.erstedigital.meetingappbackend.rest.data.response.UserResponse;
import com.erstedigital.meetingappbackend.rest.service.AttendanceService;
import com.erstedigital.meetingappbackend.rest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.stream.Collectors;

@EnableWebMvc
@CrossOrigin(origins = {"https://www.bettermeetings.sk","http://localhost:3000"}, maxAge = 3600)
@RestController
@RequestMapping(path="/user")
public class UserController {
    private final UserService userService;
    private final AttendanceService attendanceService;

    public UserController(UserService userService, AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
        this.userService = userService;
    }

    @GetMapping
    public @ResponseBody
    UserResponse getUserByMail(@RequestParam("email") String email) throws NotFoundException {
        if (email != null) {
            return new UserResponse(userService.findByEmail(email));
        } else {
            throw new NotFoundException();
        }
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody
    UserResponse getUserById(@PathVariable("id") Integer id) throws NotFoundException {
        return new UserResponse(userService.findById(id));
    }

    @PostMapping
    public @ResponseBody ResponseEntity<UserResponse> addNewUser(@RequestBody UserRequest body) throws NotFoundException {
        return new ResponseEntity<>(new UserResponse(userService.create(body)), HttpStatus.CREATED);
    }


    @PutMapping(path = "/{id}")
    public @ResponseBody UserResponse updateUser(@PathVariable("id") Integer id,
                                                 @RequestBody UserRequest body) throws NotFoundException {
        return new UserResponse(userService.update(id, body));
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody void deleteUser(@PathVariable("id") Integer id) throws NotFoundException {
        userService.delete(id);
    }

    @PostMapping(path = "/attendees")
    public @ResponseBody ResponseEntity<List<UserResponse>> addNewUsers(@RequestBody List<UserRequest> body) throws NotFoundException {
        return new ResponseEntity<>(userService.create(body).stream().map(UserResponse::new).collect(Collectors.toList()),  HttpStatus.CREATED);
    }

    @GetMapping(value = "/attendees/{id}")
    public @ResponseBody
    ResponseEntity<List<UserResponse>> getMeetingAttendees(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(userService.getMeetingAttendees(id).stream().map(attendee -> new UserResponse(attendee, id)).collect(Collectors.toList()), HttpStatus.OK);
    }
}
