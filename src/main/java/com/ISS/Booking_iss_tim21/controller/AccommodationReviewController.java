package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.AccommodationReviewDTO;
import com.ISS.Booking_iss_tim21.model.AccommodationReview;
import com.ISS.Booking_iss_tim21.service.AccommodationReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value ="api/accommodationreview")
public class AccommodationReviewController {

    @Autowired
    AccommodationReviewService service;



    @GetMapping
    public ResponseEntity<List<AccommodationReviewDTO>> getAccommodationReviews() {

        List<AccommodationReview> reviews = service.getAll();

        // convert reviews to DTOs
        List<AccommodationReviewDTO> reviewsDTO = new ArrayList<>();
        for (AccommodationReview s : reviews) {
            reviewsDTO.add(new AccommodationReviewDTO(s));
        }

        return new ResponseEntity<>(reviewsDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodationReview(@PathVariable Long id) {

        AccommodationReview review = service.findOne(id);

        if (review != null) {
            service.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
