package com.example.booking_project.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "BUSES")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(name = "license_plate", nullable = false, unique = true, length = 50)
    private String licensePlate;

    @Column(name = "seat_capacity", nullable = false)
    private int seatCapacity;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "status", nullable = false, length = 50)
    private String status;
    
    @OneToMany(mappedBy = "bus", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Schedule> schedules;

    // Constructors
    public Bus() {}

    public Bus(String licensePlate, int seatCapacity, String type, String status) {
        this.licensePlate = licensePlate;
        this.seatCapacity = seatCapacity;
        this.type = type;
        this.status = status;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
