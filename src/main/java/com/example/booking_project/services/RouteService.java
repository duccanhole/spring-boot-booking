package com.example.booking_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.booking_project.entity.Route;
import com.example.booking_project.repositories.RouteRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public Page<Route> getAllRoutes(Pageable pageable) {
        return routeRepository.findAll(pageable);
    }

    public Optional<Route> getRouteById(UUID id) {
        return routeRepository.findById(id);
    }

    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }

    public Optional<Route> updateRoute(UUID id, Route updatedRoute) {
        return routeRepository.findById(id).map(route -> {
            route.setDeparture(updatedRoute.getDeparture());
            route.setArrival(updatedRoute.getArrival());
            route.setTimeEstimate(updatedRoute.getTimeEstimate());
            return routeRepository.save(route);
        });
    }

    public void deleteRoute(UUID id) {
        routeRepository.deleteById(id);
    }
}
