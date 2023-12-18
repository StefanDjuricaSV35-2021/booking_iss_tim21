package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.AccommodationDetailsDTO;
import com.ISS.Booking_iss_tim21.dto.AccommodationPreviewDTO;
import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import com.ISS.Booking_iss_tim21.service.AccommodationFilterService;
import com.ISS.Booking_iss_tim21.service.AccommodationPricingService;
import com.ISS.Booking_iss_tim21.service.AccommodationService;
import com.ISS.Booking_iss_tim21.service.UserService;
import com.ISS.Booking_iss_tim21.utility.ImageManipulationTools;
import com.ISS.Booking_iss_tim21.utility.UrlFilterParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static com.ISS.Booking_iss_tim21.utility.ImageManipulationTools.ImagePathSetToBase64;
import static com.ISS.Booking_iss_tim21.utility.ImageManipulationTools.ImagePathToBase64;

@RestController
@RequestMapping("/api/v1/auth/accommodations")
@CrossOrigin(origins = "http://localhost:4200")
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;
    @Autowired
    private AccommodationFilterService accommodationFilterService;
    @Autowired
    private AccommodationPricingService pricingService;
    @Autowired
    private UserService userService;

    @GetMapping("/previews")
    public ResponseEntity<List<AccommodationPreviewDTO>> getAccommodationsPreviews() {
         List<Accommodation> accommodations = accommodationService.getAll();

        List<AccommodationPreviewDTO> accommodationPreviewDTOs = new ArrayList<>();
        for(Accommodation a : accommodations) {
            AccommodationPreviewDTO ap=new AccommodationPreviewDTO();

            ap.setImage(ImageManipulationTools.ImagePathToBase64(a.getPhotos().iterator().next()));
            ap.setId(a.getId());
            ap.setName(a.getName());
            ap.setLocation(a.getLocation());
            accommodationPreviewDTOs.add(ap);
        }

        return new ResponseEntity<>(accommodationPreviewDTOs, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AccommodationPreviewDTO>> getAccommodationsPreviewBySearchParams(
            @RequestParam(value="dateFrom",required = true) String dateFrom,
            @RequestParam(value="dateTo",required=true) String dateTo,
            @RequestParam(value="noGuests",required=true) Integer noGuests,
            @RequestParam(value="location",required=true) String location,
            @RequestParam(value="filters",required=false) String filters

    ) {
        List<Accommodation> validAccommodations = accommodationService.getAccommodationBySearchParams(location,noGuests,dateFrom,dateTo);

        validAccommodations=accommodationService.setPrices(validAccommodations,dateFrom,dateTo);

        validAccommodations=accommodationFilterService.filterAccommodations(validAccommodations,filters);

        List<AccommodationPreviewDTO> accommodationPreviewDTOs = new ArrayList<>();
        for(Accommodation a : validAccommodations) {

            accommodationPreviewDTOs.add(new AccommodationPreviewDTO(a));
        }

        return new ResponseEntity<>(accommodationPreviewDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationDetailsDTO> getAccommodation(@PathVariable Long id) {

        Accommodation accommodation = accommodationService.findOne(id);

        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AccommodationDetailsDTO accDTO=new AccommodationDetailsDTO();

        accDTO.setId(accommodation.getId());
        accDTO.setOwnerId(accommodation.getOwner().getId());
        accDTO.setName(accommodation.getName());
        accDTO.setType(accommodation.getType());
        accDTO.setMinGuests(accommodation.getMinGuests());
        accDTO.setMaxGuests(accommodation.getMaxGuests());
        accDTO.setDescription(accommodation.getDescription());
        accDTO.setAmenities(accommodation.getAmenities());
        accDTO.setPhotos(new ArrayList<>(ImagePathSetToBase64(new HashSet<>(accommodation.getPhotos()))));
        accDTO.setDaysForCancellation(accommodation.getDaysForCancellation());
        accDTO.setLocation(accommodation.getLocation());

        return new ResponseEntity<>(accDTO, HttpStatus.OK);
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
        accommodation.setLocation(accommodationDTO.getLocation());
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
