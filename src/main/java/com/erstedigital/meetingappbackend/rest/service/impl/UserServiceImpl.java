package com.erstedigital.meetingappbackend.rest.service.impl;

import com.erstedigital.meetingappbackend.framework.exception.NotFoundException;
import com.erstedigital.meetingappbackend.persistence.data.User;
import com.erstedigital.meetingappbackend.persistence.repository.UserRepository;
import com.erstedigital.meetingappbackend.rest.data.request.UserRequest;
import com.erstedigital.meetingappbackend.rest.service.PositionService;
import com.erstedigital.meetingappbackend.rest.service.UserService;
import org.springframework.stereotype.Service;

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
    public User create(UserRequest request) throws NotFoundException {
        return userRepository.save(new User(request, positionService.findById(request.getPositionId())));
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
            user.setUser_position(positionService.findById(request.getPositionId()));
        }
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        userRepository.delete(findById(id));
    }
}
