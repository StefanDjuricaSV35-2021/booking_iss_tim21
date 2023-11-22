package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.ReservationRequestDTO;
import com.ISS.Booking_iss_tim21.model.ReservationRequest;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationRequestStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/reservationRequests")
public class ReservationRequestController {
    //    @Autowired
    //    private ReservationRequestService requestService;

    @GetMapping
    public ResponseEntity<List<ReservationRequestDTO>> getReservationRequests() {
        // List<ReservationRequest> reservationRequests = requestService.findAll();
        List<ReservationRequest> reservationRequests = new ArrayList<>();
        reservationRequests.add(new ReservationRequest(1L, 1L, 1L, 2, 2.0, new TimeSlot(), ReservationRequestStatus.Accepted));

        List<ReservationRequestDTO> reservationRequestDTOs = new ArrayList<>();
        for(ReservationRequest r : reservationRequests) {
            reservationRequestDTOs.add(new ReservationRequestDTO(r));
        }

        return new ResponseEntity<>(reservationRequestDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationRequestDTO> getReservationRequest(@PathVariable Long id) {

        //ReservationRequest reservationRequest = requestController.findOne(id);
        ReservationRequest reservationRequest = new ReservationRequest(1L, 1L, 1L, 2, 2.0, new TimeSlot(), ReservationRequestStatus.Accepted);

        if (reservationRequest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ReservationRequestDTO(reservationRequest), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationRequestDTO> createReservationRequest(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        ReservationRequest reservationRequest = new ReservationRequest(reservationRequestDTO);
        //Long reservationRequestId = requestService.createReservationRequest(reservationRequest);
        return new ResponseEntity<>(new ReservationRequestDTO(reservationRequest), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<ReservationRequestDTO> updateReservationRequest(@RequestBody ReservationRequestDTO reservationRequestDTO) {
//      ReservationRequest reservationRequest = requestService.findOne(reservationRequestDTO.getId());
        ReservationRequest reservationRequest = new ReservationRequest(1L, 1L, 1L, 2, 2.0, new TimeSlot(), ReservationRequestStatus.Accepted);


        if (reservationRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        reservationRequest.setUserId(reservationRequestDTO.getUserId());
        reservationRequest.setAccommodationId(reservationRequestDTO.getAccommodationId());
        reservationRequest.setGuestsNumber(reservationRequestDTO.getGuestsNumber());
        reservationRequest.setPrice(reservationRequestDTO.getPrice());
        reservationRequest.setTimeSlot(reservationRequestDTO.getTimeSlot());
        reservationRequest.setStatus(reservationRequestDTO.getStatus());


//        accommodationPricing = accommodationPricing.save(accommodationPricing);
        return new ResponseEntity<>(new ReservationRequestDTO(reservationRequest), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservationRequest(@PathVariable Long id) {
//        ReservationRequest reservationRequest = requestService.findOne(id);
        ReservationRequest reservationRequest = new ReservationRequest(1L, 1L, 1L, 2, 2.0, new TimeSlot(), ReservationRequestStatus.Accepted);

        if (reservationRequest != null) {
//            requestService.remove(id);
            return  new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{userId}/reservationRequests")
    public ResponseEntity<List<ReservationRequestDTO>> getUsersReservationRequests(@PathVariable Long userId) {
//        Set<ReservationRequest> reservationRequests = requestService.getUsersRequests(userId);
        Set<ReservationRequest> reservationRequests = new HashSet<>();
        reservationRequests.add(new ReservationRequest(1L, 1L, 1L, 2, 2.0, new TimeSlot(), ReservationRequestStatus.Accepted));

        List<ReservationRequestDTO> reservationRequestDTOs = new ArrayList<>();
        for (ReservationRequest r : reservationRequests) {
            reservationRequestDTOs.add(new ReservationRequestDTO(r));
        }
        return new ResponseEntity<>(reservationRequestDTOs, HttpStatus.OK);

    }

    @GetMapping(value = "/{userId}/currentReservationRequests")
    public ResponseEntity<List<ReservationRequestDTO>> getActiveReservationRequests(@PathVariable Long userId) {
        long currentUnixTimestamp = System.currentTimeMillis() / 1000L;

//        Set<ReservationRequest> reservationRequests = requestService.getUsersRequests(userId);
        Set<ReservationRequest> reservationRequests = new HashSet<>();
        reservationRequests.add(new ReservationRequest(1L, 1L, 1L, 2, 2.0, new TimeSlot(), ReservationRequestStatus.Accepted));

        List<ReservationRequestDTO> reservationRequestDTOs = new ArrayList<>();
        for (ReservationRequest r : reservationRequests) {
            if (r.getTimeSlot().getStartDate() > currentUnixTimestamp) {
                reservationRequestDTOs.add(new ReservationRequestDTO(r));
            }
        }
        return new ResponseEntity<>(reservationRequestDTOs, HttpStatus.OK);

    }
}
