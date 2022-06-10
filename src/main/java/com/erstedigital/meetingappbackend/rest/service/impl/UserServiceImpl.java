package com.erstedigital.meetingappbackend.rest.service.impl;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.User;
import com.erstedigital.meetingappbackend.persistence.repository.UserRepository;
import com.erstedigital.meetingappbackend.rest.data.request.UserRequest;
import com.erstedigital.meetingappbackend.rest.service.PositionService;
import com.erstedigital.meetingappbackend.rest.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            if(foundUser.isPresent()) {
                users.add(foundUser.get());
            }
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
        return userRepository.save(new User(request, positionService.findById(request.getPositionId())));
    }

    @Override
    public List<User> create(List<UserRequest> request) throws NotFoundException {
        ArrayList<User> newUserList = new ArrayList<>();

        // find user by mail and create new user if he does not exist
        request.forEach(userRequest -> {
            try {
                User user = findByEmail(userRequest.getEmail());
                newUserList.add(user);
            } catch (NotFoundException e) {
                try {
                    User user = userRepository.save(new User(userRequest, positionService.findById(userRequest.getPositionId())));
                    newUserList.add(user);
                } catch (NotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

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
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        userRepository.delete(findById(id));
    }
}
