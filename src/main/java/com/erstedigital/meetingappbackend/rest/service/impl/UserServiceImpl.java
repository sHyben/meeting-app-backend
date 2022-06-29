package com.erstedigital.meetingappbackend.rest.service.impl;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.Attendance;
import com.erstedigital.meetingappbackend.persistence.data.User;
import com.erstedigital.meetingappbackend.persistence.repository.UserRepository;
import com.erstedigital.meetingappbackend.rest.data.request.UserRequest;
import com.erstedigital.meetingappbackend.rest.service.PositionService;
import com.erstedigital.meetingappbackend.rest.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PositionService positionService;

    public UserServiceImpl(UserRepository userRepository, PositionService positionService) {
        this.userRepository = userRepository;
        this.positionService = positionService;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public List<User> findById(List<Integer> id) throws NotFoundException {
        ArrayList<User> users = new ArrayList<>();

        id.forEach(user -> {
            Optional<User> foundUser = userRepository.findById(user);
            foundUser.ifPresent(users::add);
        });

        return users;
    }

    @Override
    public User findByEmail(String email) throws NotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public User create(UserRequest request) throws NotFoundException {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if(user.isPresent()) {
            return user.get();
        } else {
            if(request.getPositionId() != null) {
                return userRepository.save(new User(request, positionService.findById(request.getPositionId())));
            } else {
                return userRepository.save(new User(request, null));
            }
        }
    }

    @Override
    public List<User> create(List<UserRequest> request) throws NotFoundException {
        ArrayList<User> newUserList = new ArrayList<>();

        // find user by mail and create new user if he does not exist
        for (UserRequest userRequest: request) {
            User user = userRepository.findByEmail(userRequest.getEmail())
                    .orElseGet( () -> {
                        try {
                            return userRepository.save(new User(userRequest, positionService.findById(userRequest.getPositionId())));
                        } catch (NotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
            newUserList.add(user);
        }

        return newUserList;
    }

    @Override
    public User update(Integer id, UserRequest request) throws NotFoundException {
        User user = findById(id);
        if(request.getName() != null) {
            user.setName(request.getName());
        }
        if(request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if(request.getPositionId() != null) {
            user.setUserPosition(positionService.findById(request.getPositionId()));
        }

        user.setModifiedAt(new Date(Calendar.getInstance().getTime().getTime()));
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        userRepository.delete(findById(id));
    }

    @Override
    public List<User> getMeetingAttendees(Integer id) {
        return userRepository.findMeetingAttendees(id);
    }
}
