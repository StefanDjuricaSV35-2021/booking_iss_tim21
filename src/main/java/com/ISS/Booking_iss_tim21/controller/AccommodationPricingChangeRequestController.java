package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.AccommodationPricingChangeRequestDTO;
import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.AccommodationChangeRequest;
import com.ISS.Booking_iss_tim21.model.AccommodationPricingChangeRequest;
import com.ISS.Booking_iss_tim21.service.AccommodationChangeRequestService;
import com.ISS.Booking_iss_tim21.service.AccommodationPricingChangeRequestService;
import com.ISS.Booking_iss_tim21.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    //get all pricing requests for accommodation that is being changed
    @GetMapping(value = "/{id}")
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
    public ResponseEntity<AccommodationPricingChangeRequestDTO> getAccommodationPricingChangeRequest(@PathVariable Long id) {
        AccommodationPricingChangeRequest pricingChangeRequest = pricingChangeRequestService.findOne(id);

        if (pricingChangeRequest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AccommodationPricingChangeRequestDTO pricingChangeRequestDTO = new AccommodationPricingChangeRequestDTO(pricingChangeRequest);
        return new ResponseEntity<>(pricingChangeRequestDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
        pricingChangeRequest.setId(pricingChangeRequestDTO.getId());
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
    public ResponseEntity<Void> deleteAccommodationPricingChangeRequest(@PathVariable Long id) {
        AccommodationPricingChangeRequest existingRequest = pricingChangeRequestService.findOne(id);

        if (existingRequest != null) {
            pricingChangeRequestService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
