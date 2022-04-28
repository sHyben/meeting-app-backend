package com.erstedigital.meetingappbackend.rest.controller;

import com.erstedigital.meetingappbackend.persistence.data.User;
import com.erstedigital.meetingappbackend.persistence.repository.PositionRepository;
import com.erstedigital.meetingappbackend.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping(path="/users")
public class UserController {
    private UserRepository userRepository;
    private PositionRepository positionRepository;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/findById")
    public @ResponseBody
    Optional<User> getUserById(@RequestParam(value = "id") Integer id) {
        return userRepository.findById(id);
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser(@RequestParam String name,
                                           @RequestParam String email,
                                           @RequestParam Optional<Integer> positionId) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        positionId.ifPresent(integer -> user.setUser_position(positionRepository.getById(integer)));
        try {
            userRepository.save(user);
        } catch(DataAccessException e) {
            return "Exception: " + e.getMessage();
        }
        return "Saved";
    }

    @PutMapping(path="/update")
    public @ResponseBody String updateUser(@RequestParam Integer id,
                                           @RequestParam Optional<String> name,
                                           @RequestParam Optional<String> email,
                                           @RequestParam Optional<Integer> positionId) {
        User user = userRepository.getById(id);
        name.ifPresent(user::setName);
        email.ifPresent(user::setEmail);
        positionId.ifPresent(integer -> user.setUser_position(positionRepository.getById(integer)));

        userRepository.save(user);
        return "Updated";
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody String deleteUser(@RequestParam Integer id) {
        if(getUserById(id).isPresent()) {
            userRepository.delete(getUserById(id).get());
        }

        return "Deleted";
    }

    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private void setPositionRepository(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }
}
