package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.AccommodationPricingDTO;
import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.AccommodationPricing;
import com.ISS.Booking_iss_tim21.service.AccommodationPricingService;
import com.ISS.Booking_iss_tim21.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/pricings")
@CrossOrigin(origins = "http://localhost:4200")
public class AccommodationPricingController {
    @Autowired
    private AccommodationPricingService pricingService;
    @Autowired
    private AccommodationService accommodationService;
    @GetMapping
    public ResponseEntity<List<AccommodationPricingDTO>> getAccommodationPricings() {
        List<AccommodationPricing> accommodationPricings = pricingService.getAll();
        List<AccommodationPricingDTO> accommodationPricingDTOs = new ArrayList<>();
        for(AccommodationPricing a : accommodationPricings) {
            accommodationPricingDTOs.add(new AccommodationPricingDTO(a));
        }

        return new ResponseEntity<>(accommodationPricingDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationPricingDTO> getAccommodationPricing(@PathVariable Long id) {

        AccommodationPricing accommodationPricing = pricingService.findOne(id);

        if (accommodationPricing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AccommodationPricingDTO(accommodationPricing), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationPricingDTO> createAccommodationPricing(@RequestBody AccommodationPricingDTO accommodationPricingDTO) {
        if (accommodationPricingDTO.getAccommodationId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Accommodation accommodation = accommodationService.findOne(accommodationPricingDTO.getAccommodationId());
        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        AccommodationPricing accommodationPricing = new AccommodationPricing();
        accommodationPricing.setAccommodation(accommodation);
        accommodationPricing.setTimeSlot(accommodationPricingDTO.getTimeSlot());
        accommodationPricing.setPrice(accommodationPricingDTO.getPrice());

        pricingService.save(accommodationPricing);
        return new ResponseEntity<>(new AccommodationPricingDTO(accommodationPricing), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<AccommodationPricingDTO> updateAccommodation(@RequestBody AccommodationPricingDTO accommodationPricingDTO) {
      AccommodationPricing accommodationPricing = pricingService.findOne(accommodationPricingDTO.getId());

        if (accommodationPricing == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        accommodationPricing.setAccommodation(accommodationService.findOne(accommodationPricingDTO.getAccommodationId()));
        accommodationPricing.setTimeSlot(accommodationPricingDTO.getTimeSlot());
        accommodationPricing.setPrice(accommodationPricingDTO.getPrice());


        pricingService.save(accommodationPricing);
        return new ResponseEntity<>(new AccommodationPricingDTO(accommodationPricing), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodationPricing(@PathVariable Long id) {
        AccommodationPricing accommodationPricing = pricingService.findOne(id);

        if (accommodationPricing != null) {
            pricingService.remove(id);

            return  new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{accommodationId}/accommodationPricings")
    public ResponseEntity<List<AccommodationPricingDTO>> getPricingsForAccommodation(@PathVariable Long accommodationId) {
        List<AccommodationPricing> accommodationPricings = pricingService.getActiveAccommodationPricings(accommodationId);

        List<AccommodationPricingDTO> accommodationPricingDTOS = new ArrayList<>();
        for (AccommodationPricing a : accommodationPricings) {
            accommodationPricingDTOS.add(new AccommodationPricingDTO(a));
        }
        return new ResponseEntity<>(accommodationPricingDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/activeAccommodationPricings")
    public ResponseEntity<List<AccommodationPricingDTO>> getActivePricingsForAccommodation(@PathVariable Long accommodationId) {
        List<AccommodationPricing> accommodationPricings = pricingService.getActiveAccommodationPricings(accommodationId);

        List<AccommodationPricingDTO> accommodationPricingDTOS = new ArrayList<>();
        for (AccommodationPricing a : accommodationPricings) {
            accommodationPricingDTOS.add(new AccommodationPricingDTO(a));
        }
        return new ResponseEntity<>(accommodationPricingDTOS, HttpStatus.OK);

    }
}
