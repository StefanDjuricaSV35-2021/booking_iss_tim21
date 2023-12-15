package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.AccommodationDetailsDTO;
import com.ISS.Booking_iss_tim21.dto.AccommodationPreviewDTO;
import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import com.ISS.Booking_iss_tim21.service.AccommodationService;
import com.ISS.Booking_iss_tim21.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth/accommodations")
@CrossOrigin(origins = "http://localhost:4200")
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<AccommodationPreviewDTO>> getAccommodations() {
         List<Accommodation> accommodations = accommodationService.getAll();

        List<AccommodationPreviewDTO> accommodationPreviewDTOs = new ArrayList<>();
        for(Accommodation a : accommodations) {
            AccommodationPreviewDTO ap=new AccommodationPreviewDTO();

            byte[] bytes=null;

            try {
                bytes=Files.readAllBytes(new File(a.getPhotos().iterator().next()).toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ap.setImage(Base64.getEncoder().encodeToString(bytes));
            ap.setId(a.getId());
            ap.setName(a.getName());
            ap.setLocation(a.getLocation());
            accommodationPreviewDTOs.add(ap);
        }

        return new ResponseEntity<>(accommodationPreviewDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationDetailsDTO> getAccommodation(@PathVariable Long id) {

        Accommodation accommodation = accommodationService.findOne(id);

        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AccommodationDetailsDTO(accommodation), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDetailsDTO> createAccommodation(@RequestBody AccommodationDetailsDTO accommodationDTO) {

        if (accommodationDTO.getOwnerId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User owner = userService.findById(accommodationDTO.getOwnerId());
        if (owner == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Accommodation accommodation = new Accommodation();
        accommodation.setOwner(owner);
        accommodation.setName(accommodationDTO.getName());
        accommodation.setType(accommodationDTO.getType());
        accommodation.setMinGuests(accommodationDTO.getMinGuests());
        accommodation.setMaxGuests(accommodationDTO.getMaxGuests());
        accommodation.setDescription(accommodationDTO.getDescription());
        accommodation.setAmenities(accommodationDTO.getAmenities());
        accommodation.setPhotos(accommodationDTO.getPhotos());
        accommodation.setDaysForCancellation(accommodationDTO.getDaysForCancellation());
        accommodation.setLocation(accommodationDTO.getLocation());
        accommodation.setEnabled(accommodationDTO.isEnabled());
        accommodation.setPerNight(accommodationDTO.isPerNight());

        accommodationService.save(accommodation);

        return new ResponseEntity<>(new AccommodationDetailsDTO(accommodation), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<AccommodationDetailsDTO> updateAccommodation(@RequestBody AccommodationDetailsDTO accommodationDTO) {
        Accommodation accommodation = accommodationService.findOne(accommodationDTO.getId());

        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User owner = userService.findById(accommodationDTO.getOwnerId());
        if (owner == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        accommodation.setOwner(owner);
        accommodation.setName(accommodationDTO.getName());
        accommodation.setType(accommodationDTO.getType());
        accommodation.setMinGuests(accommodationDTO.getMinGuests());
        accommodation.setMaxGuests(accommodationDTO.getMaxGuests());
        accommodation.setDescription(accommodationDTO.getDescription());
        accommodation.setAmenities(accommodationDTO.getAmenities());
        accommodation.setPhotos(accommodationDTO.getPhotos());
        accommodation.setDaysForCancellation(accommodationDTO.getDaysForCancellation());
        accommodation.setLocation(accommodationDTO.getLocation());
        accommodation.setEnabled(accommodationDTO.isEnabled());
        accommodation.setPerNight(accommodationDTO.isPerNight());

        accommodationService.save(accommodation);
        return new ResponseEntity<>(new AccommodationDetailsDTO(accommodation), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {
        Accommodation accommodation = accommodationService.findOne(id);

        if (accommodation != null) {
            accommodationService.remove(id);
            return  new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{ownerId}/accommodations")
    public ResponseEntity<List<AccommodationPreviewDTO>> getOwnersAccommodations(@PathVariable Long ownerId) {
        List<Accommodation> ownerAccommodations = accommodationService.getOwnersAccommodations(ownerId);
        List<AccommodationPreviewDTO> accommodationsDTO = new ArrayList<>();
        for (Accommodation a : ownerAccommodations) {
            accommodationsDTO.add(new AccommodationPreviewDTO(a));
        }
        return new ResponseEntity<>(accommodationsDTO, HttpStatus.OK);

    }
}
