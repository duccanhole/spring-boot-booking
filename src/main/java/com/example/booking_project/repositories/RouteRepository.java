package com.example.booking_project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.booking_project.entity.Route;

import java.util.UUID;

public interface RouteRepository extends JpaRepository<Route, UUID> {
    Page<Route> findAll(Pageable pageable);
}
