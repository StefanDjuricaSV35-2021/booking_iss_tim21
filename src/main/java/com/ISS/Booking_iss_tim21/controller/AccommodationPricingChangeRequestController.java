package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.AccommodationPricingChangeRequestDTO;
import com.ISS.Booking_iss_tim21.model.*;
import com.ISS.Booking_iss_tim21.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/accommodationPricingChangeRequests")
@CrossOrigin(origins = "http://localhost:4200")
public class AccommodationPricingChangeRequestController {
    @Autowired
    AccommodationPricingChangeRequestService pricingChangeRequestService;

    @Autowired
    AccommodationService accommodationService;

    @Autowired
    AccommodationChangeRequestService accommodationChangeRequestService;

    @Autowired
    AccommodationPricingService accommodationPricingService;

    @Autowired
    ReservationService reservationService;

    //get all pricing requests for accommodation that is being changed
    @GetMapping(value = "/all/{accommodationChangeRequestId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<AccommodationPricingChangeRequestDTO>> getAllAccommodationPricingChangeRequests(@PathVariable Long accommodationChangeRequestId) {
        List<AccommodationPricingChangeRequest> pricingChangeRequests = pricingChangeRequestService.getAllForAccommodation(accommodationChangeRequestId);
        if (pricingChangeRequests.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        List<AccommodationPricingChangeRequestDTO> pricingChangeRequestDTOs = new ArrayList<>();
        for (AccommodationPricingChangeRequest pricingChangeRequest : pricingChangeRequests) {
            pricingChangeRequestDTOs.add(new AccommodationPricingChangeRequestDTO(pricingChangeRequest));
        }

        return new ResponseEntity<>(pricingChangeRequestDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<AccommodationPricingChangeRequestDTO> getAccommodationPricingChangeRequest(@PathVariable Long id) {
        AccommodationPricingChangeRequest pricingChangeRequest = pricingChangeRequestService.findOne(id);

        if (pricingChangeRequest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AccommodationPricingChangeRequestDTO pricingChangeRequestDTO = new AccommodationPricingChangeRequestDTO(pricingChangeRequest);
        return new ResponseEntity<>(pricingChangeRequestDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER')")
    public ResponseEntity<AccommodationPricingChangeRequestDTO> createAccommodationPricingChangeRequest(@RequestBody AccommodationPricingChangeRequestDTO pricingChangeRequestDTO) {
        if (pricingChangeRequestDTO.getAccommodationChangeRequestId() == null || pricingChangeRequestDTO.getAccommodationId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AccommodationChangeRequest accommodationChangeRequest = accommodationChangeRequestService.findOne(pricingChangeRequestDTO.getAccommodationChangeRequestId());
        if (accommodationChangeRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Accommodation accommodation = accommodationService.findOne(pricingChangeRequestDTO.getAccommodationId());
        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AccommodationPricingChangeRequest pricingChangeRequest = new AccommodationPricingChangeRequest();
        pricingChangeRequest.setAccommodationChangeRequest(accommodationChangeRequest);
        pricingChangeRequest.setStatus(pricingChangeRequestDTO.getStatus());
        pricingChangeRequest.setAccommodation(accommodation);
        pricingChangeRequest.setTimeSlot(pricingChangeRequestDTO.getTimeSlot());
        pricingChangeRequest.setPrice(pricingChangeRequestDTO.getPrice());

        pricingChangeRequestService.save(pricingChangeRequest);

        AccommodationPricingChangeRequestDTO savedDTO = new AccommodationPricingChangeRequestDTO(pricingChangeRequest);
        return new ResponseEntity<>(savedDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<AccommodationPricingChangeRequestDTO> updateAccommodationPricingChangeRequest(@PathVariable Long id, @RequestBody AccommodationPricingChangeRequestDTO updatedDTO) {
        AccommodationPricingChangeRequest existingRequest = pricingChangeRequestService.findOne(id);
        if (existingRequest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AccommodationChangeRequest accommodationChangeRequest = accommodationChangeRequestService.findOne(updatedDTO.getAccommodationChangeRequestId());
        if (accommodationChangeRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Accommodation accommodation = accommodationService.findOne(updatedDTO.getAccommodationId());
        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        existingRequest.setAccommodationChangeRequest(accommodationChangeRequest);
        existingRequest.setStatus(updatedDTO.getStatus());
        existingRequest.setAccommodation(accommodation);
        existingRequest.setTimeSlot(updatedDTO.getTimeSlot());
        existingRequest.setPrice(updatedDTO.getPrice());

        pricingChangeRequestService.save(existingRequest);

        AccommodationPricingChangeRequestDTO responseDTO = new AccommodationPricingChangeRequestDTO(existingRequest);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER')")
    public ResponseEntity<Void> deleteAccommodationPricingChangeRequest(@PathVariable Long id) {
        AccommodationPricingChangeRequest existingRequest = pricingChangeRequestService.findOne(id);

        if (existingRequest != null) {
            pricingChangeRequestService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/update/{accommodationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> updateAccommodationPricings(@PathVariable Long accommodationId, @RequestBody List<AccommodationPricingChangeRequestDTO> accommodationPricingChangeRequestDTOS) {
        accommodationPricingService.deleteAllPricingsForAccommodation(accommodationId);
        if (accommodationPricingChangeRequestDTOS.isEmpty()) return new ResponseEntity<>(HttpStatus.OK);

        List<Reservation> reservations = reservationService.getCurrentActiveReservationsForAccommodation(accommodationId);
        for(AccommodationPricingChangeRequestDTO accommodationPricingChangeRequestDTO : accommodationPricingChangeRequestDTOS) {
            pricingChangeRequestService.checkOverlapAndAdjustTimeSlots(reservations, accommodationPricingChangeRequestDTO);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
