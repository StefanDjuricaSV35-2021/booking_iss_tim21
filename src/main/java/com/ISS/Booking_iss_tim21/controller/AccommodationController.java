package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.*;
import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.TimeSlot;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/auth/accommodations")
@CrossOrigin(origins = "http://localhost:4200")
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;
    @Autowired
    private AccommodationFilterService accommodationFilterService;
    @Autowired
    AccommodationDatesService datesService;
    @Autowired
    private UserService userService;

    @GetMapping("/previews")
    public ResponseEntity<List<AccommodationPreviewDTO>> getAccommodationsPreviews() {
        List<Accommodation> accommodations = accommodationService.getAllEnabled();

        List<AccommodationPreviewDTO> accommodationPreviewDTOs = new ArrayList<>();
        for (Accommodation a : accommodations) {
            AccommodationPreviewDTO ap = new AccommodationPreviewDTO();

            ap.setImage(a.getPhotos().isEmpty() ? null : a.getPhotos().get(0));
            ap.setId(a.getId());
            ap.setName(a.getName());
            ap.setLocation(a.getLocation());
            accommodationPreviewDTOs.add(ap);
        }

        return new ResponseEntity<>(accommodationPreviewDTOs, HttpStatus.OK);
    }

    @GetMapping("/previews/notEnabled")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER')")
    public ResponseEntity<List<AccommodationPreviewDTO>> getNotEnabledAccommodationsPreviews() {
        List<Accommodation> accommodations = accommodationService.getAllNotEnabled();
        if (accommodations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<AccommodationPreviewDTO> accommodationPreviewDTOs = new ArrayList<>();
        for (Accommodation a : accommodations) {
            AccommodationPreviewDTO ap = new AccommodationPreviewDTO();

            ap.setImage(a.getPhotos().isEmpty() ? null : a.getPhotos().get(0));
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

        validAccommodations=accommodationService.setPrices(validAccommodations,dateFrom,dateTo,noGuests);

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

        AccommodationDetailsDTO accDTO=new AccommodationDetailsDTO(accommodation);

        List<TimeSlot> accDates=datesService.getAccommodationAvaiableDates(accDTO.getId());
        accDTO.setDates(accDates);

        return new ResponseEntity<>(accDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/preview/{id}")
    public ResponseEntity<AccommodationPreviewDTO> getAccommodationPreview(@PathVariable Long id) {

        Accommodation accommodation = accommodationService.findOne(id);

        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AccommodationPreviewDTO accDTO=new AccommodationPreviewDTO(accommodation);

        return new ResponseEntity<>(accDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/price")
    public ResponseEntity<Double> getAccommodationPrice(
            @RequestParam(value="dateFrom",required = true) String dateFrom,
            @RequestParam(value="dateTo",required=true) String dateTo,
            @RequestParam(value="noGuests",required=true) Integer noGuests,
            @RequestParam(value="id",required=true) Long id) {

        double price=accommodationService.getAccommodationPrice(dateFrom,dateTo,noGuests,id);
        return new ResponseEntity<>(price, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER')")
    public ResponseEntity<AccommodationDetailsDTO> createAccommodation(
            @RequestBody AccommodationDetailsDTO accommodationDTO) {

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

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<AccommodationDetailsDTO> updateAccommodation(
            @RequestBody AccommodationDetailsDTO accommodationDTO) {
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
        accommodation.setPhotos(accommodationDTO.getPhotos());
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER')")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {
        Accommodation accommodation = accommodationService.findOne(id);

        if (accommodation != null) {
            accommodationService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{ownerId}/accommodations")
    public ResponseEntity<List<AccommodationPreviewDTO>> getOwnersAccommodations(@PathVariable Long ownerId) {
        List<Accommodation> ownerAccommodations = accommodationService.getOwnersAccommodations(ownerId);

        List<AccommodationPreviewDTO> accommodationPreviewDTOs = new ArrayList<>();
        for (Accommodation a : ownerAccommodations) {
            AccommodationPreviewDTO ap = new AccommodationPreviewDTO();

            ap.setImage(a.getPhotos().isEmpty() ? null : a.getPhotos().get(0));
            ap.setId(a.getId());
            ap.setName(a.getName());
            ap.setLocation(a.getLocation());
            accommodationPreviewDTOs.add(ap);
        }
        return new ResponseEntity<>(accommodationPreviewDTOs, HttpStatus.OK);

    }

    @GetMapping(value = "/{ownerId}/profit")
    public ResponseEntity<List<AccommodationProfitDTO>> getOwnersAccommodationsProfit(
            @PathVariable Long ownerId,
            @RequestParam(value="dateFrom",required = true) String dateFrom,
            @RequestParam(value="dateTo",required=true) String dateTo) {

        List<AccommodationProfitDTO> accommodationProfitDTOS = accommodationService.getOwnerAccommodationsProfit(ownerId,dateFrom,dateTo);
        return new ResponseEntity<>(accommodationProfitDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/annual-data")
    public ResponseEntity<AccommodationAnnualDataDTO> getAccommodationAnnualData(
            @PathVariable Long id,
            @RequestParam(value="year",required = true) Integer year) {

        AccommodationAnnualDataDTO accommodationAnnualData = accommodationService.getAccommodationAnnualData(id,year);
        return new ResponseEntity<>(accommodationAnnualData, HttpStatus.OK);
    }

    @GetMapping(value = "/{ownerId}/res-count")
    public ResponseEntity<List<AccommodationReservationCountDTO>> getOwnersAccommodationsReservationCount(
            @PathVariable Long ownerId,
            @RequestParam(value="dateFrom",required = true) String dateFrom,
            @RequestParam(value="dateTo",required=true) String dateTo) {

        List<AccommodationReservationCountDTO> accommodationReservationCountDTOS = accommodationService.getOwnerAccommodationsReservationCount(ownerId,dateFrom,dateTo);
        return new ResponseEntity<>(accommodationReservationCountDTOS, HttpStatus.OK);
    }

}
