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
    private static List<ReservationRequest> reservationRequests = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<ReservationRequestDTO>> getReservationRequests() {
        // List<ReservationRequest> reservationRequests = requestService.findAll();

        List<ReservationRequestDTO> reservationRequestDTOs = new ArrayList<>();
        for(ReservationRequest r : reservationRequests) {
            reservationRequestDTOs.add(new ReservationRequestDTO(r));
        }

        return new ResponseEntity<>(reservationRequestDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationRequestDTO> getReservationRequest(@PathVariable Long id) {

        //ReservationRequest reservationRequest = requestController.findOne(id);
        ReservationRequest reservationRequest = findReservationRequestById(id);

        if (reservationRequest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ReservationRequestDTO(reservationRequest), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationRequestDTO> createReservationRequest(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        ReservationRequest reservationRequest = new ReservationRequest(reservationRequestDTO);

        //Long reservationRequestId = requestService.createReservationRequest(reservationRequest);
        reservationRequests.add(reservationRequest);

        return new ResponseEntity<>(new ReservationRequestDTO(reservationRequest), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<ReservationRequestDTO> updateReservationRequest(@RequestBody ReservationRequestDTO reservationRequestDTO) {
//      ReservationRequest reservationRequest = requestService.findOne(reservationRequestDTO.getId());
        ReservationRequest reservationRequest = findReservationRequestById(reservationRequestDTO.getId());

        if (reservationRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        reservationRequest.setUserId(reservationRequestDTO.getUserId());
        reservationRequest.setAccommodationId(reservationRequestDTO.getAccommodationId());
        reservationRequest.setGuestsNumber(reservationRequestDTO.getGuestsNumber());
        reservationRequest.setPrice(reservationRequestDTO.getPrice());
        reservationRequest.setTimeSlot(reservationRequestDTO.getTimeSlot());
        reservationRequest.setStatus(reservationRequestDTO.getStatus());


//        reservationRequest = requestService.save(reservationRequest);
        return new ResponseEntity<>(new ReservationRequestDTO(reservationRequest), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservationRequest(@PathVariable Long id) {
//        ReservationRequest reservationRequest = requestService.findOne(id);
        ReservationRequest reservationRequest = findReservationRequestById(id);

        if (reservationRequest != null) {
//            requestService.remove(id);
            reservationRequests.remove(reservationRequest);

            return  new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{userId}/reservationRequests")
    public ResponseEntity<List<ReservationRequestDTO>> getUsersReservationRequests(@PathVariable Long userId) {
//        Set<ReservationRequest> reservationRequests = requestService.getUsersRequests(userId);
        Set<ReservationRequest> userReservationRequests = getUsersReservationRequestsById(userId);
        List<ReservationRequestDTO> reservationRequestDTOs = new ArrayList<>();
        for (ReservationRequest r : userReservationRequests) {
            reservationRequestDTOs.add(new ReservationRequestDTO(r));
        }
        return new ResponseEntity<>(reservationRequestDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/currentReservationRequests")
    public ResponseEntity<List<ReservationRequestDTO>> getActiveReservationRequests(@PathVariable Long userId) {
//        Set<ReservationRequest> reservationRequests = requestService.getUsersRequests(userId);
        Set<ReservationRequest> reservationRequest = getCurrentReservationRequestsByUserId(userId);

        List<ReservationRequestDTO> reservationRequestDTOs = new ArrayList<>();
        for (ReservationRequest r : reservationRequest) {
            reservationRequestDTOs.add(new ReservationRequestDTO(r));
        }
        return new ResponseEntity<>(reservationRequestDTOs, HttpStatus.OK);

    }


    // Temporary method
    private ReservationRequest findReservationRequestById(Long id) {
        for (ReservationRequest r : reservationRequests) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    // Temporary method
    private Set<ReservationRequest> getUsersReservationRequestsById(Long userId) {
        Set<ReservationRequest> userReservationRequests = new HashSet<>();
        for (ReservationRequest r : reservationRequests) {
            if (r.getUserId().equals(userId)) {
                userReservationRequests.add(r);
            }
        }
        return userReservationRequests;
    }

    // Temporary method
    private Set<ReservationRequest> getCurrentReservationRequestsByUserId(Long userId) {
        long currentUnixTimestamp = System.currentTimeMillis() / 1000L;
        Set<ReservationRequest> currentReservationRequests = new HashSet<>();
        for (ReservationRequest r : reservationRequests) {
            if (r.getUserId().equals(userId) && r.getTimeSlot().getStartDate() > currentUnixTimestamp) {
                currentReservationRequests.add(r);
            }
        }
        return currentReservationRequests;
    }
}
