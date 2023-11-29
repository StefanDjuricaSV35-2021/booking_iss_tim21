package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.AccommodationPricingDTO;
import com.ISS.Booking_iss_tim21.model.AccommodationPricing;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pricings")
public class AccommodationPricingController {
    //    @Autowired
    //    private AccommodationPricingService pricingService;
    private static List<AccommodationPricing> accommodationPricings = new ArrayList<>();

    static {
        accommodationPricings.add(new AccommodationPricing(1L, 1L, new TimeSlot(), 1.0));
        accommodationPricings.add(new AccommodationPricing(2L, 1L, new TimeSlot(), 1.0));
    }
    @GetMapping
    public ResponseEntity<List<AccommodationPricingDTO>> getAccommodationPricings() {
        List<AccommodationPricingDTO> accommodationPricingDTOs = new ArrayList<>();
        for(AccommodationPricing a : accommodationPricings) {
            accommodationPricingDTOs.add(new AccommodationPricingDTO(a));
        }

        return new ResponseEntity<>(accommodationPricingDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationPricingDTO> getAccommodationPricing(@PathVariable Long id) {

        //AccommodationPricing accommodationPrice = pricingController.findOne(id);
        AccommodationPricing accommodationPricing =findAccommodationPricingById(id);

        if (accommodationPricing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AccommodationPricingDTO(accommodationPricing), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationPricingDTO> createAccommodationPricing(@RequestBody AccommodationPricingDTO accommodationPricingDTO) {
        AccommodationPricing accommodationPricing = new AccommodationPricing(accommodationPricingDTO);
        //Long accommodationPricingId = pricingService.createAccommodation(accommodation);
        accommodationPricings.add(accommodationPricing);
        return new ResponseEntity<>(new AccommodationPricingDTO(accommodationPricing), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<AccommodationPricingDTO> updateAccommodation(@RequestBody AccommodationPricingDTO accommodationPricingDTO) {
//      AccommodationPricing accommodationPricing = pricingService.findOne(AccommodationPricingDTO.getId());
        AccommodationPricing accommodationPricing = findAccommodationPricingById(accommodationPricingDTO.getId());


        if (accommodationPricing == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        accommodationPricing.setAccommodationId(accommodationPricingDTO.getAccommodationId());
        accommodationPricing.setTimeSlot(accommodationPricingDTO.getTimeSlot());
        accommodationPricing.setPrice(accommodationPricingDTO.getPrice());


//        accommodationPricing = pricingService.save(accommodationPricing);
        return new ResponseEntity<>(new AccommodationPricingDTO(accommodationPricing), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodationPricing(@PathVariable Long id) {
//        AccommodationPricing accommodationPricing = pricingService.findOne(id);
        AccommodationPricing accommodationPricing = findAccommodationPricingById(id);

        if (accommodationPricing != null) {
//            pricingService.remove(id);
            accommodationPricings.remove(accommodationPricing);

            return  new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{accommodationId}/accommodationPricings")
    public ResponseEntity<List<AccommodationPricingDTO>> getPricingsForAccommodation(@PathVariable Long accommodationId) {
//        Set<AccommodationPricing> accommodationPricings = pricingService.getAccommodationsPricings(accommodationId);
        Set<AccommodationPricing> accommodationPricings = getAccommodationPricingsForAccommodation(accommodationId);

        List<AccommodationPricingDTO> accommodationPricingDTOS = new ArrayList<>();
        for (AccommodationPricing a : accommodationPricings) {
            accommodationPricingDTOS.add(new AccommodationPricingDTO(a));
        }
        return new ResponseEntity<>(accommodationPricingDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{accommodationId}/activeAccommodationPricings")
    public ResponseEntity<List<AccommodationPricingDTO>> getActivePricingsForAccommodation(@PathVariable Long accommodationId) {
//        Set<AccommodationPricing> accommodationPricings = pricingService.getActiveAccommodationsPricings(accommodationId);
        Set<AccommodationPricing> activeAccommodationPricings = getActiveAccommodationPricings(accommodationId);

        List<AccommodationPricingDTO> accommodationPricingDTOS = new ArrayList<>();
        for (AccommodationPricing a : activeAccommodationPricings) {
            accommodationPricingDTOS.add(new AccommodationPricingDTO(a));
        }
        return new ResponseEntity<>(accommodationPricingDTOS, HttpStatus.OK);

    }



    // Temporary function
    private AccommodationPricing findAccommodationPricingById(Long id) {
        return accommodationPricings.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Temporary function
    private Set<AccommodationPricing> getAccommodationPricingsForAccommodation(Long accommodationId) {
        return accommodationPricings.stream()
                .filter(a -> a.getAccommodationId().equals(accommodationId))
                .collect(Collectors.toSet());
    }

    // Temporary function
    private Set<AccommodationPricing> getActiveAccommodationPricings(Long accommodationId) {
        long currentUnixTimestamp = System.currentTimeMillis() / 1000L;
        return accommodationPricings.stream()
                .filter(a -> a.getAccommodationId().equals(accommodationId) && a.getTimeSlot().getEndDate() > currentUnixTimestamp)
                .collect(Collectors.toSet());
    }
}
