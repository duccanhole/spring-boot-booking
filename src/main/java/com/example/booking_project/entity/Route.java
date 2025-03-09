package com.example.booking_project.entity;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ROUTES")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(name = "departure", nullable = false, length = 255)
    private String departure;

    @Column(name = "arrival", nullable = false, length = 255)
    private String arrival;

    @Column(name = "time_estimate", nullable = false)
    private LocalTime timeEstimate;
    
    @OneToMany(mappedBy = "route", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Schedule> schedules;

    // Constructors
    public Route() {}

    public Route(String departure, String arrival, LocalTime timeEstimate) {
        this.departure = departure;
        this.arrival = arrival;
        this.timeEstimate = timeEstimate;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public LocalTime getTimeEstimate() {
        return timeEstimate;
    }

    public void setTimeEstimate(LocalTime timeEstimate) {
        this.timeEstimate = timeEstimate;
    }
}
