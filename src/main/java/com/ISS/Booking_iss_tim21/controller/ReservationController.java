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
    private static List<Reservation> reservations = new ArrayList<>();


    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getReservations() {
        // List<Reservation> reservations = reservationService.findAll();

        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for(Reservation r : reservations) {
            reservationDTOs.add(new ReservationDTO(r));
        }

        return new ResponseEntity<>(reservationDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {

        //Reservation reservation = reservationController.findOne(id);
        Reservation reservation = findReservationById(id);

        if (reservation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ReservationDTO(reservation), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation(reservationDTO);

        //Long reservationId = reservationService.createReservation(reservation);
        reservations.add(reservation);

        return new ResponseEntity<>(new ReservationDTO(reservation), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<ReservationDTO> updateReservation(@RequestBody ReservationDTO reservationDTO) {
//      Reservation reservation = reservationService.findOne(reservationDTO.getId());
        Reservation reservation = findReservationById(reservationDTO.getId());

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
        Reservation reservation = findReservationById(id);

        if (reservation != null) {

//            reservationService.remove(id);
            reservations.remove(reservation);

            return  new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{userId}/reservations")
    public ResponseEntity<List<ReservationDTO>> getUsersReservations(@PathVariable Long userId) {
//        Set<Reservation> reservations = reservationService.getUsersReservations(userId);
        Set<Reservation> reservation = getUsersReservationsById(userId);

        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for (Reservation r : reservation) {
            reservationDTOs.add(new ReservationDTO(r));
        }
        return new ResponseEntity<>(reservationDTOs, HttpStatus.OK);

    }

    @GetMapping(value = "/{userId}/currentReservations")
    public ResponseEntity<List<ReservationDTO>> getActiveReservations(@PathVariable Long userId) {

//        Set<Reservation> reservationRequests = requestService.getUsersReservations(userId);
        Set<Reservation> reservations = getActiveReservationsById(userId);

        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for (Reservation r : reservations) {
            reservationDTOs.add(new ReservationDTO(r));
        }
        return new ResponseEntity<>(reservationDTOs, HttpStatus.OK);

    }



    // Temporary method
    private Reservation findReservationById(Long id) {
        for (Reservation r : reservations) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    // Temporary method
    private Set<Reservation> getUsersReservationsById(Long userId) {
        Set<Reservation> userReservations = new HashSet<>();
        for (Reservation r : reservations) {
            if (r.getUserId().equals(userId)) {
                userReservations.add(r);
            }
        }
        return userReservations;
    }

    // Temporary method
    private Set<Reservation> getActiveReservationsById(Long userId) {
        long currentUnixTimestamp = System.currentTimeMillis() / 1000L;
        Set<Reservation> activeReservations = new HashSet<>();
        for (Reservation r : reservations) {
            if (r.getUserId().equals(userId) && r.getTimeSlot().getStartDate() > currentUnixTimestamp) {
                activeReservations.add(r);
            }
        }
        return activeReservations;
    }
}
