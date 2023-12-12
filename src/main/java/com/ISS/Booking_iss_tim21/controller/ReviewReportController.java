package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.ReviewReportDTO;
import com.ISS.Booking_iss_tim21.model.ReviewReport;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.review.Review;
import com.ISS.Booking_iss_tim21.service.ReviewReportService;
import com.ISS.Booking_iss_tim21.service.ReviewService;
import com.ISS.Booking_iss_tim21.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/reports/reviews")
public class ReviewReportController {
    @Autowired
    private ReviewReportService reviewReportService;

    @Autowired
    UserService userService;

    @Autowired
    ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewReportDTO>> getReviewReports() {

        List<ReviewReport> reports = reviewReportService.getAll();

        // convert reports to DTOs
        List<ReviewReportDTO> reportsDTO = new ArrayList<>();
        for (ReviewReport s : reports) {
            reportsDTO.add(new ReviewReportDTO(s));
        }

        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewReportDTO> createOwnerReview(@RequestBody ReviewReportDTO reviewReportDTO) {

        if (reviewReportDTO.getReportedReviewId() == null || reviewReportDTO.getReporterId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User reporter = userService.findById(reviewReportDTO.getReporterId());
        Review review = reviewService.findOne(reviewReportDTO.getReportedReviewId());

        if (reporter == null || review == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        ReviewReport report = new ReviewReport();
        report.setUser(reporter);
        report.setReview(review);
        report.setId(reviewReportDTO.getId());
        report.setDescription(reviewReportDTO.getDescription());
        reviewReportService.save(report);



        return new ResponseEntity<>(new ReviewReportDTO(report), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReviewReport(@PathVariable Long id) {

        ReviewReport report = reviewReportService.findOne(id);

        if (report != null) {
            reviewReportService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
