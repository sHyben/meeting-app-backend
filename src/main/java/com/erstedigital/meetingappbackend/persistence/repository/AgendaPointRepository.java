package com.erstedigital.meetingappbackend.persistence.repository;

import com.erstedigital.meetingappbackend.persistence.data.AgendaPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaPointRepository extends JpaRepository<AgendaPoint, Integer> {

}
