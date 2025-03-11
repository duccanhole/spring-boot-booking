package com.example.booking_project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.booking_project.entity.Seat;

import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, UUID> {
    Page<Seat> findAll(Pageable pageable);
    
    @Query("SELECT COUNT(s) > 0 FROM Seat s JOIN Booking b ON s.id = b.seat.id WHERE s.seatNumber = :seatNumber AND s.schedule.id = :scheduleId AND LOWER(b.status) IN ('created', 'confirmed')")
    boolean existsBySeatNumber(@Param("seatNumber") String seat, @Param("scheduleId") UUID id);
    @Query("SELECT COUNT(s) > 0 FROM Seat s JOIN Booking b ON s.id = b.seat.id WHERE s.id != :id AND s.seatNumber = :seatNumber AND s.schedule.id = :scheduleId AND LOWER(b.status) IN ('created', 'confirmed')")
    boolean existsBySeatNumber(@Param("seatNumber") String seat, @Param("scheduleId") UUID schedule, @Param("id") UUID id);
}
