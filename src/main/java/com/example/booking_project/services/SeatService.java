package com.example.booking_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.booking_project.entity.Seat;
import com.example.booking_project.repositories.SeatRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public Page<Seat> getAllSeats(Pageable pageable) {
        return seatRepository.findAll(pageable);
    }

    public Seat getSeatById(UUID id) {
        return seatRepository.findById(id).orElseThrow(() -> new RuntimeException("No Seat found."));
    }

    public Seat createSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public Seat updateSeat(UUID id, Seat updatedSeat) {
        return seatRepository.findById(id).map(seat -> {
            seat.setSchedule(updatedSeat.getSchedule());
            seat.setSeatNumber(updatedSeat.getSeatNumber());
            return seatRepository.save(seat);
        }).orElseThrow(() -> new RuntimeException("No Seat found."));
    }

    public void deleteSeat(UUID id) {
        seatRepository.deleteById(id);
    }
}
