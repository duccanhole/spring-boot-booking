package com.example.booking_project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.booking_project.entity.Seat;

import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, UUID> {
    Page<Seat> findAll(Pageable pageable);
}
