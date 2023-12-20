package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.OwnerReviewDTO;
import com.ISS.Booking_iss_tim21.model.review.OwnerReview;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.service.OwnerReviewService;
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
@RequestMapping("/api/v1/auth/reviews/owners")
public class OwnerReviewController {

    @Autowired
    private OwnerReviewService ownerReviewService;

    @Autowired
    private UserService userService;




    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<List<OwnerReviewDTO>> getOwnerReviews() {

        List<OwnerReview> reviews = ownerReviewService.getAll();

        // convert reviews to DTOs
        List<OwnerReviewDTO> reviewsDTO = new ArrayList<>();
        for (OwnerReview s : reviews) {
            reviewsDTO.add(new OwnerReviewDTO(s));
        }

        return new ResponseEntity<>(reviewsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<List<OwnerReviewDTO>> getOwnerReviews(@PathVariable Long id) {
        //Accommodation accommodation = accommodationService.findOne(id);
        List<OwnerReview> reviews= ownerReviewService.getAllByOwnerId(id);
        if (reviews == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<OwnerReviewDTO> reviewDTOS=new ArrayList<OwnerReviewDTO>();
        for (OwnerReview n:reviews) {
            reviewDTOS.add(new OwnerReviewDTO(n));
        }
        return new ResponseEntity<>(reviewDTOS, HttpStatus.OK);
    }




    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_GUEST')")
    public ResponseEntity<OwnerReviewDTO> createOwnerReview(@RequestBody OwnerReviewDTO ownerReviewDTO) {

        if (ownerReviewDTO.getReviewerId() == null || ownerReviewDTO.getReviewedId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

       User reviewer = userService.findById(ownerReviewDTO.getReviewerId());
        User reviewed = userService.findById(ownerReviewDTO.getReviewedId());

        if (reviewer == null || reviewed == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        OwnerReview review = new OwnerReview();
        review.setReviewer(reviewer);
        review.setReviewed(reviewed);
        review.setId(ownerReviewDTO.getId());
        review.setComment(ownerReviewDTO.getComment());
        review.setRating(ownerReviewDTO.getRating());

        ownerReviewService.save(review);

        return new ResponseEntity<>(new OwnerReviewDTO(review), HttpStatus.CREATED);
    }
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_GUEST')")
    public ResponseEntity<Void> deleteOwnerReview(@PathVariable Long id) {

        OwnerReview review = ownerReviewService.findOne(id);

        if (review != null) {
            ownerReviewService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
