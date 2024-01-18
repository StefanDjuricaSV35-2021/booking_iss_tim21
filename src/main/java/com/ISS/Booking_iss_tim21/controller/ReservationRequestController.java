package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.ReservationRequestDTO;
import com.ISS.Booking_iss_tim21.model.ReservationRequest;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationRequestStatus;
import com.ISS.Booking_iss_tim21.service.AccommodationService;
import com.ISS.Booking_iss_tim21.service.ReservationRequestService;
import com.ISS.Booking_iss_tim21.service.ReservationService;
import com.ISS.Booking_iss_tim21.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/reservationRequests")
public class ReservationRequestController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRequestService requestService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccommodationService accommodationService;
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ReservationRequestDTO>> getReservationRequests() {
        List<ReservationRequest> reservationRequests = requestService.getAll();

        List<ReservationRequestDTO> reservationRequestDTOs = new ArrayList<>();
        for(ReservationRequest r : reservationRequests) {
            reservationRequestDTOs.add(new ReservationRequestDTO(r));
        }

        return new ResponseEntity<>(reservationRequestDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<ReservationRequestDTO> getReservationRequest(@PathVariable Long id) {

        ReservationRequest reservationRequest = requestService.findOne(id);

        if (reservationRequest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ReservationRequestDTO(reservationRequest), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_GUEST')")
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

        if(reservationRequest.getAccommodation().isAutoAccepting()){
            reservationRequest.setStatus(ReservationRequestStatus.Accepted);
            reservationService.acceptReservation(reservationRequest);
        }

        requestService.save(reservationRequest);

        return new ResponseEntity<>(new ReservationRequestDTO(reservationRequest), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<ReservationRequestDTO> updateReservationRequest(@RequestBody ReservationRequestDTO reservationRequestDTO) {
      ReservationRequest reservationRequest = requestService.findOne(reservationRequestDTO.getId());
        ReservationRequestStatus statusOriginal = reservationRequest.getStatus();
        if (reservationRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        reservationRequest.setUser(userService.findById(reservationRequestDTO.getUserId()));
        reservationRequest.setAccommodation(accommodationService.findOne(reservationRequestDTO.getAccommodationId()));
        reservationRequest.setGuestsNumber(reservationRequestDTO.getGuestsNumber());
        reservationRequest.setPrice(reservationRequestDTO.getPrice());
        reservationRequest.setTimeSlot(reservationRequestDTO.getTimeSlot());
        reservationRequest.setStatus(reservationRequestDTO.getStatus());
        if(reservationRequest.getStatus() == ReservationRequestStatus.Accepted && statusOriginal != ReservationRequestStatus.Accepted) {
            reservationService.acceptReservation(reservationRequest);
        }

        requestService.save(reservationRequest);
        return new ResponseEntity<>(new ReservationRequestDTO(reservationRequest), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<List<ReservationRequestDTO>> getUsersReservationRequests(@PathVariable Long userId) {

        List<ReservationRequest> reservationRequests = requestService.getUsersReservationRequestsById(userId);
        List<ReservationRequestDTO> reservationRequestDTOs = new ArrayList<>();
        for (ReservationRequest r : reservationRequests) {
            reservationRequestDTOs.add(new ReservationRequestDTO(r));


        }

        return new ResponseEntity<>(reservationRequestDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/ownerReservationRequests")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<List<ReservationRequestDTO>> getOwnerReservationRequests(@PathVariable Long userId) {

        List<ReservationRequest> reservationRequests = requestService.getUsersReservationRequestsByOwnerId(userId);
        List<ReservationRequestDTO> reservationRequestDTOs = new ArrayList<>();
        for (ReservationRequest r : reservationRequests) {
            reservationRequestDTOs.add(new ReservationRequestDTO(r));


        }

        return new ResponseEntity<>(reservationRequestDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/currentReservationRequests")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<List<ReservationRequestDTO>> getCurrentReservationRequests(@PathVariable Long userId) {
        List<ReservationRequest> reservationRequests = requestService.getCurrentReservationRequestsById(userId);

        List<ReservationRequestDTO> reservationRequestDTOs = new ArrayList<>();
        for (ReservationRequest r : reservationRequests) {
            reservationRequestDTOs.add(new ReservationRequestDTO(r));
        }
        return new ResponseEntity<>(reservationRequestDTOs, HttpStatus.OK);

    }
}
