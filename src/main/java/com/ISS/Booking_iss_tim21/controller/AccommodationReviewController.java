package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.AccommodationReviewDTO;
import com.ISS.Booking_iss_tim21.dto.OwnerReviewDTO;
import com.ISS.Booking_iss_tim21.model.AccommodationReview;
import com.ISS.Booking_iss_tim21.model.OwnerReview;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.service.AccommodationReviewService;
import com.ISS.Booking_iss_tim21.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value ="reviews/accommodations")
public class AccommodationReviewController {

    @Autowired
    AccommodationReviewService accommodationReviewService;

    @Autowired
    UserService userService;



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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationReviewDTO> createOwnerReview(@RequestBody AccommodationReviewDTO accommodationReviewDTO) {
//
//        if (accommodationReviewDTO.ge == null || accommodationReviewDTO.getReviewedId() == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        User reviewer = userService.findOne(accommodationReviewDTO.getReviewerId());
//        Accommodation reviewed = userService.findOne(accommodationReviewDTO.getAccommodationId());
//
//        if (reviewer == null || reviewed == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        AccommodationReview review = new AccommodationReview();
//        review.setReviewer(userService.findOne(ownerReviewDTO.getReviewerId()));
//        review.setReviewed(userService.findOne(ownerReviewDTO.getReviewedId()));
//        review.setId(ownerReviewDTO.getId());
//        review.setComment(ownerReviewDTO.getComment());
//        review.setRating(ownerReviewDTO.getRating());
//
//        accommodationReviewService.save(review);
//
//        return new ResponseEntity<>(new OwnerReviewDTO(review), HttpStatus.CREATED);
        return null;
    }

    @DeleteMapping(value = "/{id}")
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
