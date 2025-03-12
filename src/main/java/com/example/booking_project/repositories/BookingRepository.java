package com.example.booking_project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.booking_project.entity.Booking;
import com.example.booking_project.entity.Schedule;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    Page<Booking> findAll(Pageable pageable);
    
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId")
    Page<Booking> searchBookingByUser(Pageable pageable, @Param("userId") UUID userId);
}
