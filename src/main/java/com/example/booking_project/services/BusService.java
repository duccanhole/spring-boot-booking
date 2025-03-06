package com.example.booking_project.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.booking_project.entity.Bus;
import com.example.booking_project.repositories.BusRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class BusService {
    private final BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public Page<Bus> getAllBuses(Pageable pageable) {
        return busRepository.findAll(pageable);
    }

    public Bus getBusById(UUID id) {
        return busRepository.findById(id).orElseThrow(() -> new RuntimeException("No bus found."));
    }

    public Bus createBus(Bus bus) {
        return busRepository.save(bus);
    }

    public Bus updateBus(UUID id, Bus busDetails) {
        return busRepository.findById(id)
            .map(bus -> {
                bus.setLicensePlate(busDetails.getLicensePlate());
                bus.setSeatCapacity(busDetails.getSeatCapacity());
                bus.setType(busDetails.getType());
                bus.setStatus(busDetails.getStatus());
                return busRepository.save(bus);
            }).orElseThrow(() -> new RuntimeException("Bus not found"));
    }

    public void deleteBus(UUID id) {
        busRepository.deleteById(id);
    }
}
