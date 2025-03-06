package com.example.booking_project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.booking_project.entity.Bus;

import java.util.UUID;

public interface BusRepository extends JpaRepository<Bus, UUID> {
    Page<Bus> findAll(Pageable pageable);
}
