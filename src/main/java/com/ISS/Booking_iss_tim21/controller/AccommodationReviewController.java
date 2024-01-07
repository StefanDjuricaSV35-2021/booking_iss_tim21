package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.AccommodationReviewDTO;
import com.ISS.Booking_iss_tim21.dto.OwnerReviewDTO;
import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.review.AccommodationReview;
import com.ISS.Booking_iss_tim21.model.review.OwnerReview;
import com.ISS.Booking_iss_tim21.service.AccommodationReviewService;
import com.ISS.Booking_iss_tim21.service.AccommodationService;
import com.ISS.Booking_iss_tim21.service.ReservationService;
import com.ISS.Booking_iss_tim21.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/auth/reviews/accommodations")
public class AccommodationReviewController {

    @Autowired
    AccommodationReviewService accommodationReviewService;

    @Autowired
    UserService userService;

    @Autowired
    AccommodationService accommodationService;

    @Autowired
    ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<AccommodationReviewDTO>> getAccommodationReviews() {

        List<AccommodationReview> reviews = accommodationReviewService.getAll();

        // convert reviews to DTOs
        List<AccommodationReviewDTO> reviewsDTO = new ArrayList<>();
        for (AccommodationReview s : reviews) {
            reviewsDTO.add(new AccommodationReviewDTO(s));
        }

        return new ResponseEntity<>(reviewsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<List<AccommodationReviewDTO>> getAccommodationsReviews(@PathVariable Long id) {
        List<AccommodationReview> reviews= accommodationReviewService.findAllByAccommodationId(id);
        if (reviews == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<AccommodationReviewDTO> reviewDTOS=new ArrayList<AccommodationReviewDTO>();
        for (AccommodationReview n:reviews) {
            reviewDTOS.add(new AccommodationReviewDTO(n));
        }
        return new ResponseEntity<>(reviewDTOS, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_GUEST')")
    public ResponseEntity<AccommodationReviewDTO> createAccommodationReview(@RequestBody AccommodationReviewDTO accommodationReviewDTO) {

        if (accommodationReviewDTO.getAccommodationId() == null || accommodationReviewDTO.getReviewerId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User reviewer = userService.findById(accommodationReviewDTO.getReviewerId());
        Accommodation reviewed = accommodationService.findOne(accommodationReviewDTO.getAccommodationId());

        if (reviewer == null || reviewed == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!reservationService.isValidForReview(reviewer.getId(), reviewed.getId())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AccommodationReview review = new AccommodationReview();
        review.setReviewer(reviewer);
        review.setReviewed(reviewed);
        review.setComment(accommodationReviewDTO.getComment());
        review.setRating(accommodationReviewDTO.getRating());

        accommodationReviewService.save(review);

        return new ResponseEntity<>(new AccommodationReviewDTO(review), HttpStatus.CREATED);

    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteAccommodationReview(@PathVariable Long id) {

        AccommodationReview review = accommodationReviewService.findOne(id);

        if (review != null) {
            accommodationReviewService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
