package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.ReservationRequest;
import com.ISS.Booking_iss_tim21.repository.ReservationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationRequestService {
    @Autowired
    ReservationRequestRepository repository;

    public List<ReservationRequest> getAll(){
        return repository.findAll();
    }


    public ReservationRequest findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }
    public void save(ReservationRequest reservationRequest) { repository.save(reservationRequest); }

    public void remove(Long id) {
        repository.deleteById(id);
    }

    public List<ReservationRequest> getUsersReservationRequestsById(Long userId) { return repository.getUsersReservationRequestsById(userId); }

    public List<ReservationRequest> getCurrentReservationRequestsById(Long userId) {
        long currentUnixTimestamp = System.currentTimeMillis() / 1000L;
        List<ReservationRequest> allReservationRequests = repository.getUsersReservationRequestsById(userId);
        List<ReservationRequest> currentReservationRequests = new ArrayList<>();
        for (ReservationRequest r : allReservationRequests) {
            if (r.getTimeSlot().getStartDate() > currentUnixTimestamp)
                currentReservationRequests.add(r);
        }
        return currentReservationRequests;
    }
    public List<ReservationRequest> getUsersReservationRequestsByOwnerId(Long userId) {return repository.getUsersReservationRequestsByOwnerId(userId);}
}
