package com.example.booking_project.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "BOOKINGS")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uniqueidentifier")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_BOOKING_USER"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false, foreignKey = @ForeignKey(name = "FK_BOOKING_SEAT"))
    private Seat seat;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    // Constructors
    public Booking() {}

    public Booking(User user, Seat seat, String status) {
        this.user = user;
        this.seat = seat;
        this.status = status;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
