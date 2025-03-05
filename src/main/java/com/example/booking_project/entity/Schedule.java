package com.example.booking_project.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "SCHEDULES")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false, foreignKey = @ForeignKey(name = "FK_SCHEDULE_BUS"))
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false, foreignKey = @ForeignKey(name = "FK_SCHEDULE_DRIVER"))
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false, foreignKey = @ForeignKey(name = "FK_SCHEDULE_ROUTE"))
    private Route route;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Seat> seats;

    // Constructors
    public Schedule() {}

    public Schedule(Bus bus, Driver driver, Route route, LocalDateTime departureTime, BigDecimal price) {
        this.bus = bus;
        this.driver = driver;
        this.route = route;
        this.departureTime = departureTime;
        this.price = price;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
