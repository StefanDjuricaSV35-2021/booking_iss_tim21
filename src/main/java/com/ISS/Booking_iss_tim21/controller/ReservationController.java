package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.ReservationDTO;
import com.ISS.Booking_iss_tim21.model.Reservation;
import com.ISS.Booking_iss_tim21.model.ReservationRequest;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationRequestStatus;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus;
import com.ISS.Booking_iss_tim21.service.AccommodationService;
import com.ISS.Booking_iss_tim21.service.ReservationService;
import com.ISS.Booking_iss_tim21.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@RestController
@RequestMapping("/api/v1/auth/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccommodationService accommodationService;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ReservationDTO>> getReservations() {
        List<Reservation> reservations = reservationService.getAll();

        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for(Reservation r : reservations) {
            reservationDTOs.add(new ReservationDTO(r));
        }

        return new ResponseEntity<>(reservationDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {

        Reservation reservation = reservationService.findOne(id);

        if (reservation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ReservationDTO(reservation), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER')")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
//        if (reservationDTO.getUserId() == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        User user = userService.findOne(reservationDTO.getUserId());
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }


        Reservation reservation = new Reservation();
        reservation.setId(reservationDTO.getId());
        reservation.setUser(userService.findById(reservationDTO.getUserId()));
        reservation.setAccommodation(accommodationService.findOne(reservationDTO.getAccommodationId()));
        reservation.setGuestsNumber(reservationDTO.getGuestsNumber());
        reservation.setPrice(reservationDTO.getPrice());
        reservation.setTimeSlot(reservationDTO.getTimeSlot());
        reservation.setStatus(reservationDTO.getStatus());


        reservationService.save(reservation);

        return new ResponseEntity<>(new ReservationDTO(reservation), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<ReservationDTO> updateReservation(@RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = reservationService.findOne(reservationDTO.getId());

        if (reservation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        reservation.setUser(userService.findById(reservationDTO.getUserId()));
        reservation.setAccommodation(accommodationService.findOne(reservationDTO.getAccommodationId()));
        reservation.setGuestsNumber(reservationDTO.getGuestsNumber());
        reservation.setPrice(reservationDTO.getPrice());
        reservation.setTimeSlot(reservationDTO.getTimeSlot());
        reservation.setStatus(reservationDTO.getStatus());


        reservationService.save(reservation);
        return new ResponseEntity<>(new ReservationDTO(reservation), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteReservationRequest(@PathVariable Long id) {
        Reservation reservation= reservationService.findOne(id);

        if (reservation != null) {
            reservationService.remove(id);
            return  new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{userId}/reservations")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<List<ReservationDTO>> getUsersReservations(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.getUsersReservationsById(userId);

        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for (Reservation r : reservations) {
            reservationDTOs.add(new ReservationDTO(r));
        }
        return new ResponseEntity<>(reservationDTOs, HttpStatus.OK);

    }

    @GetMapping(value = "/{userId}/currentReservations")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<List<ReservationDTO>> getCurrentReservations(@PathVariable Long userId) {

        List<Reservation> reservations = reservationService.getCurrentReservationsById(userId);

        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for (Reservation r : reservations) {
            reservationDTOs.add(new ReservationDTO(r));
        }
        return new ResponseEntity<>(reservationDTOs, HttpStatus.OK);

    }
}
