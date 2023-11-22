package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.ReservationDTO;
import com.ISS.Booking_iss_tim21.model.Reservation;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@RestController
@RequestMapping("/reservations")
public class ReservationController {
    //    @Autowired
    //    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getReservations() {
        // List<Reservation> reservations = reservationService.findAll();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(1L, 1L, 1L, 2, 2.0, new TimeSlot(), ReservationStatus.Active));

        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for(Reservation r : reservations) {
            reservationDTOs.add(new ReservationDTO(r));
        }

        return new ResponseEntity<>(reservationDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {

        //Reservation reservation = reservationController.findOne(id);
        Reservation reservation = new Reservation(1L, 1L, 1L, 2, 2.0, new TimeSlot(), ReservationStatus.Active);

        if (reservation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ReservationDTO(reservation), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation(reservationDTO);
        //Long reservationId = reservationService.createReservation(reservation);
        return new ResponseEntity<>(new ReservationDTO(reservation), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<ReservationDTO> updateReservation(@RequestBody ReservationDTO reservationDTO) {
//      Reservation reservation = reservationService.findOne(reservationDTO.getId());
        Reservation reservation = new Reservation(1L, 1L, 1L, 2, 2.0, new TimeSlot(), ReservationStatus.Active);


        if (reservation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        reservation.setUserId(reservationDTO.getUserId());
        reservation.setAccommodationId(reservationDTO.getAccommodationId());
        reservation.setGuestsNumber(reservationDTO.getGuestsNumber());
        reservation.setPrice(reservationDTO.getPrice());
        reservation.setTimeSlot(reservationDTO.getTimeSlot());
        reservation.setStatus(reservationDTO.getStatus());


//        reservation = reservationService.save(reservation);
        return new ResponseEntity<>(new ReservationDTO(reservation), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservationRequest(@PathVariable Long id) {
//        Reservation reservation= reservationService.findOne(id);
        Reservation reservation = new Reservation(1L, 1L, 1L, 2, 2.0, new TimeSlot(), ReservationStatus.Active);

        if (reservation != null) {
//            reservationService.remove(id);
            return  new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{userId}/reservations")
    public ResponseEntity<List<ReservationDTO>> getUsersReservations(@PathVariable Long userId) {
//        Set<Reservation> reservations = reservationService.getUsersReservations(userId);
        Set<Reservation> reservation = new HashSet<>();
        reservation.add(new Reservation(1L, 1L, 1L, 2, 2.0, new TimeSlot(), ReservationStatus.Active));

        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for (Reservation r : reservation) {
            reservationDTOs.add(new ReservationDTO(r));
        }
        return new ResponseEntity<>(reservationDTOs, HttpStatus.OK);

    }

    @GetMapping(value = "/{userId}/currentReservations")
    public ResponseEntity<List<ReservationDTO>> getActiveReservations(@PathVariable Long userId) {
        long currentUnixTimestamp = System.currentTimeMillis() / 1000L;

//        Set<Reservation> reservationRequests = requestService.getUsersReservations(userId);
        Set<Reservation> reservations = new HashSet<>();
        reservations.add(new Reservation(1L, 1L, 1L, 2, 2.0, new TimeSlot(), ReservationStatus.Active));

        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getTimeSlot().getStartDate() > currentUnixTimestamp) {
                reservationDTOs.add(new ReservationDTO(r));
            }
        }
        return new ResponseEntity<>(reservationDTOs, HttpStatus.OK);

    }
}
