package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.config.AppConfig;
import com.ISS.Booking_iss_tim21.dto.ReservationRequestDTO;
import com.ISS.Booking_iss_tim21.exception.DatesOverlapException;
import com.ISS.Booking_iss_tim21.exception.InvalidIdException;
import com.ISS.Booking_iss_tim21.exception.NullIdException;
import com.ISS.Booking_iss_tim21.model.*;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationRequestStatus;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus;
import com.ISS.Booking_iss_tim21.repository.ReservationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Service
public class ReservationRequestService {
    @Autowired
    ReservationRequestRepository repository;

    @Autowired
    ReservationService reservationService;

    @Autowired
    UserService userService;

    @Autowired
    AccommodationService accommodationService;
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

    public ReservationRequest createRequest(ReservationRequestDTO reservationRequestDTO) throws Exception {

        if (reservationRequestDTO.getUserId() == null) {
            throw new NullIdException("User id is null");
        }

        User user = userService.findById(reservationRequestDTO.getUserId());
        if (user == null) {
            throw new InvalidIdException("User id is invalid");
        }

        if (reservationRequestDTO.getAccommodationId() == null) {
            throw new NullIdException("Accommodation id is null");
        }
        Accommodation acc = accommodationService.findOne(reservationRequestDTO.getAccommodationId());
        if (acc == null) {
            throw new InvalidIdException("Accommodation id is invalid");
        }

        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setId(reservationRequestDTO.getId());
        reservationRequest.setUser(user);
        reservationRequest.setAccommodation(acc);
        reservationRequest.setGuestsNumber(reservationRequestDTO.getGuestsNumber());
        reservationRequest.setPrice(reservationRequestDTO.getPrice());
        reservationRequest.setTimeSlot(reservationRequestDTO.getTimeSlot());
        reservationRequest.setStatus(reservationRequestDTO.getStatus());

        List<Reservation> reservations=reservationService.getActiveAccommodationReservations(reservationRequest.getAccommodation().getId());
        Boolean overlap=reservationRequestOverlapsActiveReservation(reservationRequest,reservations);

        if(overlap){
            throw new DatesOverlapException("Dates are overlapping with another reservation");
        }else{

            if(reservationRequest.getAccommodation().isAutoAccepting()){
                reservationRequest.setStatus(ReservationRequestStatus.Accepted);
                reservationService.acceptReservation(reservationRequest);
            }

            save(reservationRequest);

            return reservationRequest;
        }

    }

    public ReservationRequest updateReservationRequest(ReservationRequestDTO reservationRequestDTO) throws NullIdException, InvalidIdException {
        ReservationRequest reservationRequest = findOne(reservationRequestDTO.getId());
        if (reservationRequestDTO.getUserId() == null) {
            throw new NullIdException("User id is null");
        }

        User user = userService.findById(reservationRequestDTO.getUserId());
        if (user == null) {
            throw new InvalidIdException("User id is invalid");
        }

        if (reservationRequestDTO.getAccommodationId() == null) {
            throw new NullIdException("Accommodation id is null");
        }
        Accommodation acc = accommodationService.findOne(reservationRequestDTO.getAccommodationId());
        if (acc == null) {
            throw new InvalidIdException("Accommodation id is invalid");
        }
        ReservationRequestStatus statusOriginal = reservationRequest.getStatus();
        reservationRequest.setUser(user);
        reservationRequest.setAccommodation(acc);
        reservationRequest.setGuestsNumber(reservationRequestDTO.getGuestsNumber());
        reservationRequest.setPrice(reservationRequestDTO.getPrice());
        reservationRequest.setTimeSlot(reservationRequestDTO.getTimeSlot());
        reservationRequest.setStatus(reservationRequestDTO.getStatus());
        if(reservationRequest.getStatus() == ReservationRequestStatus.Accepted && statusOriginal != ReservationRequestStatus.Accepted) {
            reservationService.acceptReservation(reservationRequest);
        }
        save(reservationRequest);
        return reservationRequest;
    }

    Boolean reservationRequestOverlapsActiveReservation(ReservationRequest request,List<Reservation> reservations){

        for(Reservation r:reservations){
            if(max(r.getTimeSlot().getStartDate(), request.getTimeSlot().getStartDate()) < min(r.getTimeSlot().getEndDate(), request.getTimeSlot().getEndDate())){
                return true;
            }
        }

        return false;

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
