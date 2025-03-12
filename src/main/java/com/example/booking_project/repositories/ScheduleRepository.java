package com.example.booking_project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.booking_project.entity.Schedule;

import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    Page<Schedule> findAll(Pageable pageable);
    
    @Query("SELECT s FROM Schedule s WHERE s.route.departure = :departure AND s.route.arrival = :arrival")
    Page<Schedule> searchScheduleByRoute(Pageable pageable, @Param("departure") String departure, @Param("arrival") String arrival);
}
