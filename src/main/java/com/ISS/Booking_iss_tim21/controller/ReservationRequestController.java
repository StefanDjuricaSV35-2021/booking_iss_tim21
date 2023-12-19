package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.ReservationRequestDTO;
import com.ISS.Booking_iss_tim21.model.ReservationRequest;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationRequestStatus;
import com.ISS.Booking_iss_tim21.service.AccommodationService;
import com.ISS.Booking_iss_tim21.service.ReservationRequestService;
import com.ISS.Booking_iss_tim21.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth/reservationRequests")
public class ReservationRequestController {
    @Autowired
    private ReservationRequestService requestService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccommodationService accommodationService;
    @GetMapping
    public ResponseEntity<List<ReservationRequestDTO>> getReservationRequests() {
        List<ReservationRequest> reservationRequests = requestService.getAll();

        List<ReservationRequestDTO> reservationRequestDTOs = new ArrayList<>();
        for(ReservationRequest r : reservationRequests) {
            reservationRequestDTOs.add(new ReservationRequestDTO(r));
        }

        return new ResponseEntity<>(reservationRequestDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationRequestDTO> getReservationRequest(@PathVariable Long id) {

        ReservationRequest reservationRequest = requestService.findOne(id);

        if (reservationRequest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ReservationRequestDTO(reservationRequest), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationRequestDTO> createReservationRequest(@RequestBody ReservationRequestDTO reservationRequestDTO) {

//        if (reservationRequestDTO.getUserId() == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        User user = userService.findOne(reservationRequestDTO.getUserId());
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }


        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setId(reservationRequestDTO.getId());
        reservationRequest.setUser(userService.findById(reservationRequestDTO.getUserId()));
        reservationRequest.setAccommodation(accommodationService.findOne(reservationRequestDTO.getAccommodationId()));
        reservationRequest.setGuestsNumber(reservationRequestDTO.getGuestsNumber());
        reservationRequest.setPrice(reservationRequestDTO.getPrice());
        reservationRequest.setTimeSlot(reservationRequestDTO.getTimeSlot());
        reservationRequest.setStatus(reservationRequestDTO.getStatus());


        requestService.save(reservationRequest);

        return new ResponseEntity<>(new ReservationRequestDTO(reservationRequest), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<ReservationRequestDTO> updateReservationRequest(@RequestBody ReservationRequestDTO reservationRequestDTO) {
      ReservationRequest reservationRequest = requestService.findOne(reservationRequestDTO.getId());

        if (reservationRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        reservationRequest.setUser(userService.findById(reservationRequestDTO.getUserId()));
        reservationRequest.setAccommodation(accommodationService.findOne(reservationRequestDTO.getAccommodationId()));
        reservationRequest.setGuestsNumber(reservationRequestDTO.getGuestsNumber());
        reservationRequest.setPrice(reservationRequestDTO.getPrice());
        reservationRequest.setTimeSlot(reservationRequestDTO.getTimeSlot());
        reservationRequest.setStatus(reservationRequestDTO.getStatus());


        requestService.save(reservationRequest);
        return new ResponseEntity<>(new ReservationRequestDTO(reservationRequest), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservationRequest(@PathVariable Long id) {
        ReservationRequest reservationRequest = requestService.findOne(id);

        if (reservationRequest != null) {
            requestService.remove(id);
            return  new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{userId}/reservationRequests")
    public ResponseEntity<List<ReservationRequestDTO>> getUsersReservationRequests(@PathVariable Long userId) {
        List<ReservationRequest> reservationRequests = requestService.getUsersReservationRequestsById(userId);
        List<ReservationRequestDTO> reservationRequestDTOs = new ArrayList<>();
        for (ReservationRequest r : reservationRequests) {
            reservationRequestDTOs.add(new ReservationRequestDTO(r));
        }
        return new ResponseEntity<>(reservationRequestDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/currentReservationRequests")
    public ResponseEntity<List<ReservationRequestDTO>> getCurrentReservationRequests(@PathVariable Long userId) {
        List<ReservationRequest> reservationRequests = requestService.getCurrentReservationRequestsById(userId);

        List<ReservationRequestDTO> reservationRequestDTOs = new ArrayList<>();
        for (ReservationRequest r : reservationRequests) {
            reservationRequestDTOs.add(new ReservationRequestDTO(r));
        }
        return new ResponseEntity<>(reservationRequestDTOs, HttpStatus.OK);

    }
}
