package com.example.booking_project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.booking_project.entity.Booking;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    Page<Booking> findAll(Pageable pageable);
}
