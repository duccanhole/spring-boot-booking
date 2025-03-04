package com.example.booking_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
class RouteRequestDTO {

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
    public ResponseEntity<Page<Route>> getAllRoutes(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(routeService.getAllRoutes(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Route> getRouteById(@PathVariable UUID id) {
        Optional<Route> route = routeService.getRouteById(id);
        return route.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Route> createRoute(@Valid @RequestBody RouteRequestDTO routeRequest) {
        Route route = new Route();
        route.setDeparture(routeRequest.departure);
        route.setArrival(routeRequest.arrival);
        route.setTimeEstimate(LocalTime.parse(routeRequest.timeEstimate));

        return ResponseEntity.ok(routeService.createRoute(route));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable UUID id, @Valid @RequestBody RouteRequestDTO routeRequest) {
        Route updatedRoute = new Route();
        updatedRoute.setDeparture(routeRequest.departure);
        updatedRoute.setArrival(routeRequest.arrival);
        updatedRoute.setTimeEstimate(LocalTime.parse(routeRequest.timeEstimate));

        return routeService.updateRoute(id, updatedRoute)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable UUID id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }
}
