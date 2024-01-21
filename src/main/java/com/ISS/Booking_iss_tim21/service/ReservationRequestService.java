package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.config.AppConfig;
import com.ISS.Booking_iss_tim21.model.ReservationRequest;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationRequestStatus;
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
        return repository.findById(id).orElse(null);
    }
    public void save(ReservationRequest reservationRequest) { repository.save(reservationRequest); }

    public void remove(Long id) {
        repository.deleteById(id);
    }

    public List<ReservationRequest> getUsersReservationRequestsById(Long userId) { return repository.getUsersReservationRequestsById(userId); }

    public List<ReservationRequest> getAccommodationReservationRequestsById(Long accommodationId) { return repository.getAccommodationReservationRequestsById(accommodationId); }

    public int getUsersCancellaionCount(Long userId){

        int count=0;

        List<ReservationRequest> ress=getUsersReservationRequestsById(userId);

        for(ReservationRequest r:ress){
            if(r.getStatus()== ReservationRequestStatus.Cancelled){
                count++;
            }
        }

        return count;

    }

    public List<ReservationRequest> getCurrentReservationRequestsById(Long userId) {
        long currentUnixTimestamp = System.currentTimeMillis() / AppConfig.UNIX_DIFF;
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
