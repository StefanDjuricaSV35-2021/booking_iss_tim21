package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.AccommodationDetailsDTO;
import com.ISS.Booking_iss_tim21.dto.AccommodationPreviewDTO;
import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import com.ISS.Booking_iss_tim21.service.AccommodationService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/accommodations")
public class AccommodationController {
//    @Autowired
//    private AccommodationService accommodationService;

    @GetMapping
    public ResponseEntity<List<AccommodationPreviewDTO>> getAccommodations() {
        // List<Accommodation> accommodations = accommodationService.findAll();
        List<Accommodation> accommodations = new ArrayList<>();
        accommodations.add(new Accommodation(1L, 1L, "name", AccommodationType.Apartment, 1, 2, "a", new HashSet<Amenity>(), new HashSet<String>(), 2));

        List<AccommodationPreviewDTO> accommodationPreviewDTOs = new ArrayList<>();
        for(Accommodation a : accommodations) {
            accommodationPreviewDTOs.add(new AccommodationPreviewDTO(a));
        }

        return new ResponseEntity<>(accommodationPreviewDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationDetailsDTO> getAccommodation(@PathVariable Long id) {

        //Accommodation accommodation = accommodationService.findOne(id);
        Accommodation accommodation = new Accommodation(1L, 1L, "name", AccommodationType.Apartment, 1, 2, "a", new HashSet<Amenity>(), new HashSet<String>(), 2);


        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AccommodationDetailsDTO(accommodation), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDetailsDTO> createAccommodation(@RequestBody AccommodationDetailsDTO accommodationDTO) {
        Accommodation accommodation = new Accommodation(accommodationDTO);
        //Long accommodationId = accommodationService.createAccommodation(accommodation);
        return new ResponseEntity<>(new AccommodationDetailsDTO(accommodation), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<AccommodationDetailsDTO> updateAccommodation(@RequestBody AccommodationDetailsDTO accommodationDTO) {
//      Accommodation accommodation = accommodationService.findOne(accommodationDetailsDTO.getId());
        Accommodation accommodation = new Accommodation(1L, 1L, "name", AccommodationType.Apartment, 1, 2, "a", new HashSet<Amenity>(), new HashSet<String>(), 2);

        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        accommodation.setOwnerId(accommodationDTO.getOwnerId());
        accommodation.setName(accommodationDTO.getName());
        accommodation.setType(accommodationDTO.getType());
        accommodation.setMinGuests(accommodationDTO.getMinGuests());
        accommodation.setMaxGuests(accommodationDTO.getMaxGuests());
        accommodation.setDescription(accommodationDTO.getDescription());
        accommodation.setAmenities(accommodationDTO.getAmenities());
        accommodation.setPhotos(accommodationDTO.getPhotos());
        accommodation.setDaysForCancellation(accommodationDTO.getDaysForCancellation());

//        accommodation = accommodationService.save(accommodation);
        return new ResponseEntity<>(new AccommodationDetailsDTO(accommodation), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {
//        Accommodation accommodation = accommodationService.findOne(id);
        Accommodation accommodation = new Accommodation(1L, 1L, "name", AccommodationType.Apartment, 1, 2, "a", new HashSet<Amenity>(), new HashSet<String>(), 2);

        if (accommodation != null) {
//            accommodationService.remove(id);
            return  new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{ownerId}/accommodations")
    public ResponseEntity<List<AccommodationPreviewDTO>> getOwnersAccommodations(@PathVariable Long ownerId) {
//        Set<Accommodation> accommodations = accommodationService.getOwnersAccommodations(ownerId);
        Set<Accommodation> accommodations = new HashSet<>();
        accommodations.add(new Accommodation(1L, 1L, "name", AccommodationType.Apartment, 1, 2, "a", new HashSet<Amenity>(), new HashSet<String>(), 2));

        List<AccommodationPreviewDTO> accommodationsDTO = new ArrayList<>();
        for (Accommodation a : accommodations) {
            accommodationsDTO.add(new AccommodationPreviewDTO(a));
        }
        return new ResponseEntity<>(accommodationsDTO, HttpStatus.OK);

    }

}
