package com.example.booking_project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.booking_project.entity.Driver;

import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {
    Page<Driver> findAll(Pageable pageable);
}
