package com.example.booking_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.booking_project.entity.Route;
import com.example.booking_project.services.RouteService;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
class RouteRequest {

    @JsonProperty("departure")
    @NotBlank(message = "Departure location is required.")
    public String departure;

    @JsonProperty("arrival")
    @NotBlank(message = "Arrival location is required.")
    public String arrival;

    @JsonProperty("time_estimate")
    @NotNull(message = "Time estimate is required.")
    public String timeEstimate; // Format: HH:mm:ss
}

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping
    public Page<Route> getAllRoutes(Pageable pageable) {
        return routeService.getAllRoutes(pageable);
    }

    @GetMapping("/{id}")
    public Route getRouteById(@PathVariable("id") UUID id) {
        return routeService.getRouteById(id);
    }

    @PostMapping
    public Route createRoute(@RequestBody RouteRequest routeRequest) {
        Route route = new Route();
        route.setDeparture(routeRequest.departure);
        route.setArrival(routeRequest.arrival);
        route.setTimeEstimate(LocalTime.parse(routeRequest.timeEstimate));
        return routeService.createRoute(route);
    }

    @PutMapping("/{id}")
    public Route updateRoute(@PathVariable("id") UUID id, @RequestBody RouteRequest routeRequest) {
        Route updatedRoute = new Route();
        updatedRoute.setDeparture(routeRequest.departure);
        updatedRoute.setArrival(routeRequest.arrival);
        updatedRoute.setTimeEstimate(LocalTime.parse(routeRequest.timeEstimate));

        return routeService.updateRoute(id, updatedRoute);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable("id") UUID id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }
}
