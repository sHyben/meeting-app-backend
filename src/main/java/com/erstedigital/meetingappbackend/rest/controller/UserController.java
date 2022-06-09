package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.rest.data.request.UserRequest;
import com.erstedigital.meetingappbackend.rest.data.response.UserResponse;
import com.erstedigital.meetingappbackend.rest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "https://www.bettermeetings.sk", maxAge = 3600)
@RestController
@RequestMapping(path="/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public @ResponseBody
    List<UserResponse> getAllUsers() {
        return userService.getAll().stream().map(UserResponse::new).collect(Collectors.toList());
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

    @PutMapping(path="/{id}")
    public @ResponseBody UserResponse updateUser(@PathVariable("id") Integer id,
                                                 @RequestBody UserRequest body) throws NotFoundException {
        return new UserResponse(userService.update(id, body));
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody void deleteUser(@PathVariable("id") Integer id) throws NotFoundException {
        userService.delete(id);
    }
}
