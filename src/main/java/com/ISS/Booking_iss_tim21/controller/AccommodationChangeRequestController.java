package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.AccommodationChangeRequestDTO;
import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.AccommodationChangeRequest;
import com.ISS.Booking_iss_tim21.service.AccommodationChangeRequestService;
import com.ISS.Booking_iss_tim21.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/accommodationChangeRequests")
@CrossOrigin(origins = "http://localhost:4200")
public class AccommodationChangeRequestController {
    @Autowired
    AccommodationChangeRequestService accommodationChangeRequestService;

    @Autowired
    AccommodationService accommodationService;


    //TODO: figure out how to return pictures
    @GetMapping
    public ResponseEntity<List<AccommodationChangeRequestDTO>> getPendingAccommodationChangeRequests() {
        List<AccommodationChangeRequest> accommodationChangeRequests = accommodationChangeRequestService.getAllPending();
        if(accommodationChangeRequests.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        List<AccommodationChangeRequestDTO> accommodationChangeRequestDTOs = new ArrayList<>();
        for(AccommodationChangeRequest a : accommodationChangeRequests) {
            accommodationChangeRequestDTOs.add(new AccommodationChangeRequestDTO(a));
        }

        return new ResponseEntity<>(accommodationChangeRequestDTOs, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationChangeRequestDTO> getAccommodationChangeRequest(@PathVariable Long id) {
        AccommodationChangeRequest accommodationChangeRequest = accommodationChangeRequestService.findOne(id);

        if (accommodationChangeRequest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AccommodationChangeRequestDTO accommodationChangeRequestDTO = new AccommodationChangeRequestDTO(accommodationChangeRequest);
        return new ResponseEntity<>(accommodationChangeRequestDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationChangeRequestDTO> createAccommodationChangeRequest(@RequestBody AccommodationChangeRequestDTO accommodationChangeRequestDTO) {
        if (accommodationChangeRequestDTO.getAccommodationId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Accommodation accommodation = accommodationService.findOne(accommodationChangeRequestDTO.getAccommodationId());
        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AccommodationChangeRequest accommodationChangeRequest = new AccommodationChangeRequest();
        accommodationChangeRequest.setId(accommodationChangeRequestDTO.getId());
        accommodationChangeRequest.setRequestCreationDate(accommodationChangeRequestDTO.getRequestCreationDate());
        accommodationChangeRequest.setStatus(accommodationChangeRequestDTO.getStatus());
        accommodationChangeRequest.setAccommodation(accommodation);
        accommodationChangeRequest.setOwnerId(accommodationChangeRequestDTO.getOwnerId());
        accommodationChangeRequest.setName(accommodationChangeRequestDTO.getName());
        accommodationChangeRequest.setType(accommodationChangeRequestDTO.getType());
        accommodationChangeRequest.setMinGuests(accommodationChangeRequestDTO.getMinGuests());
        accommodationChangeRequest.setMaxGuests(accommodationChangeRequestDTO.getMaxGuests());
        accommodationChangeRequest.setDescription(accommodationChangeRequestDTO.getDescription());
        accommodationChangeRequest.setLocation(accommodationChangeRequestDTO.getLocation());
        accommodationChangeRequest.setAmenities(accommodationChangeRequestDTO.getAmenities());
        accommodationChangeRequest.setPhotos(accommodationChangeRequestDTO.getPhotos());
        accommodationChangeRequest.setDaysForCancellation(accommodationChangeRequestDTO.getDaysForCancellation());


        accommodationChangeRequestService.save(accommodationChangeRequest);

        AccommodationChangeRequestDTO savedDTO = new AccommodationChangeRequestDTO(accommodationChangeRequest);
        return new ResponseEntity<>(savedDTO, HttpStatus.CREATED);
    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationChangeRequestDTO> updateAccommodationChangeRequest(@RequestBody AccommodationChangeRequestDTO accommodationChangeRequestDTO) {
        AccommodationChangeRequest accommodationChangeRequest = accommodationChangeRequestService.findOne(accommodationChangeRequestDTO.getId());
        if (accommodationChangeRequest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Accommodation accommodation = accommodationService.findOne(accommodationChangeRequestDTO.getAccommodationId());
        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        accommodationChangeRequest.setId(accommodationChangeRequestDTO.getId());
        accommodationChangeRequest.setRequestCreationDate(accommodationChangeRequestDTO.getRequestCreationDate());
        accommodationChangeRequest.setStatus(accommodationChangeRequestDTO.getStatus());
        accommodationChangeRequest.setAccommodation(accommodation);
        accommodationChangeRequest.setOwnerId(accommodationChangeRequestDTO.getOwnerId());
        accommodationChangeRequest.setName(accommodationChangeRequestDTO.getName());
        accommodationChangeRequest.setType(accommodationChangeRequestDTO.getType());
        accommodationChangeRequest.setMinGuests(accommodationChangeRequestDTO.getMinGuests());
        accommodationChangeRequest.setMaxGuests(accommodationChangeRequestDTO.getMaxGuests());
        accommodationChangeRequest.setDescription(accommodationChangeRequestDTO.getDescription());
        accommodationChangeRequest.setLocation(accommodationChangeRequestDTO.getLocation());
        accommodationChangeRequest.setAmenities(accommodationChangeRequestDTO.getAmenities());
        accommodationChangeRequest.setPhotos(accommodationChangeRequestDTO.getPhotos());
        accommodationChangeRequest.setDaysForCancellation(accommodationChangeRequestDTO.getDaysForCancellation());

        accommodationChangeRequestService.save(accommodationChangeRequest);

        AccommodationChangeRequestDTO updatedDTO = new AccommodationChangeRequestDTO(accommodationChangeRequest);
        return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodationChangeRequest(@PathVariable Long id) {

        AccommodationChangeRequest existingRequest = accommodationChangeRequestService.findOne(id);

        if (existingRequest != null) {
            accommodationChangeRequestService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
