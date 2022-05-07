package com.erstedigital.meetingappbackend.persistence.repository;

import com.erstedigital.meetingappbackend.persistence.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByName(String name);
    Optional<User> findByEmail(String email);
}