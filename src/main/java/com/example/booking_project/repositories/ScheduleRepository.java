package com.example.booking_project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.booking_project.entity.Schedule;

import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    Page<Schedule> findAll(Pageable pageable);
}
