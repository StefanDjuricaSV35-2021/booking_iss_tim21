package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.ReviewReportDTO;
import com.ISS.Booking_iss_tim21.model.ReviewReport;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.review.AccommodationReview;
import com.ISS.Booking_iss_tim21.model.review.OwnerReview;
import com.ISS.Booking_iss_tim21.model.review.Review;
import com.ISS.Booking_iss_tim21.service.ReviewReportService;
import com.ISS.Booking_iss_tim21.service.ReviewService;
import com.ISS.Booking_iss_tim21.service.UserService;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/reports/reviews")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewReportController {
    @Autowired
    private ReviewReportService reviewReportService;

    @Autowired
    UserService userService;

    @Autowired
    ReviewService reviewService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<List<ReviewReportDTO>> getReviewReports() {

        List<ReviewReport> reports = reviewReportService.getAll();

        // convert reports to DTOs
        List<ReviewReportDTO> reportsDTO = new ArrayList<>();
        for (ReviewReport s : reports) {
            reportsDTO.add(new ReviewReportDTO(s));
        }

        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/owner")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<List<ReviewReportDTO>> getOwnerReviewReports() {

        List<ReviewReport> reports = reviewReportService.getAll();
        List<ReviewReport> ownerReviewReports = new ArrayList<>();
        for (ReviewReport r : reports) {
            Review review = reviewService.findOne(r.getReview().getId());
            Review unwrappedReview = (Review) Hibernate.unproxy(review);
            if (unwrappedReview instanceof OwnerReview) ownerReviewReports.add(r);
        }

        // convert reports to DTOs
        List<ReviewReportDTO> reportsDTO = new ArrayList<>();
        for (ReviewReport s : ownerReviewReports) {
            reportsDTO.add(new ReviewReportDTO(s));
        }

        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<List<ReviewReportDTO>> getAccommodationReviewReports() {

        List<ReviewReport> reports = reviewReportService.getAll();
        List<ReviewReport> accommodationReviewReports = new ArrayList<>();
        for (ReviewReport r : reports) {
            Review review = reviewService.findOne(r.getReview().getId());
            Review unwrappedReview = (Review) Hibernate.unproxy(review);
            if (unwrappedReview instanceof AccommodationReview) accommodationReviewReports.add(r);
        }

        // convert reports to DTOs
        List<ReviewReportDTO> reportsDTO = new ArrayList<>();
        for (ReviewReport s : accommodationReviewReports) {
            reportsDTO.add(new ReviewReportDTO(s));
        }

        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<ReviewReportDTO> getReviewReports(@PathVariable Long id) {

        ReviewReport report = reviewReportService.findOne(id);
        ReviewReportDTO reviewReportDTO = new ReviewReportDTO(report);

        return new ResponseEntity<>(reviewReportDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_GUEST', 'ROLE_OWNER')")
    public ResponseEntity<ReviewReportDTO> createOwnerReviewReport(@RequestBody ReviewReportDTO reviewReportDTO) {

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
        reviewReportService.save(report);

        return new ResponseEntity<>(new ReviewReportDTO(report), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_GUEST')")
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
