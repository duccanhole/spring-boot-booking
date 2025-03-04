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

    public Optional<Seat> getSeatById(UUID id) {
        return seatRepository.findById(id);
    }

    public Seat createSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public Optional<Seat> updateSeat(UUID id, Seat updatedSeat) {
        return seatRepository.findById(id).map(seat -> {
//            seat.setScheduleId(updatedSeat.getScheduleId());
            seat.setSeatNumber(updatedSeat.getSeatNumber());
            return seatRepository.save(seat);
        });
    }

    public void deleteSeat(UUID id) {
        seatRepository.deleteById(id);
    }
}
