package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.Reservation;
import com.ISS.Booking_iss_tim21.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository repository;

    public List<Reservation> getAll(){
        return repository.findAll();
    }


    public Reservation findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }
    public void save(Reservation reservation) { repository.save(reservation); }

    public void remove(Long id) {
        repository.deleteById(id);
    }

    public List<Reservation> getUsersReservationsById(Long userId) { return repository.getUsersReservationsById(userId); }

    public List<Reservation> getCurrentReservationsById(Long userId) {
        long currentUnixTimestamp = System.currentTimeMillis() / 1000L;
        List<Reservation> allReservations = repository.getUsersReservationsById(userId);
        List<Reservation> currentreservations = new ArrayList<>();
        for (Reservation r : allReservations) {
            if (r.getTimeSlot().getStartDate() > currentUnixTimestamp)
                currentreservations.add(r);
        }
        return currentreservations;
    }
}
