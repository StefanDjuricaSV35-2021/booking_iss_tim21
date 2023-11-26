package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.ReviewReportDTO;
import com.ISS.Booking_iss_tim21.model.ReviewReport;
import com.ISS.Booking_iss_tim21.service.ReviewReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reports/review")
public class ReviewReportController {
    @Autowired
    private ReviewReportService service;

    @GetMapping
    public ResponseEntity<List<ReviewReportDTO>> getReviewReports() {

        List<ReviewReport> reports = service.getAll();

        // convert reports to DTOs
        List<ReviewReportDTO> reportsDTO = new ArrayList<>();
        for (ReviewReport s : reports) {
            reportsDTO.add(new ReviewReportDTO(s));
        }

        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReviewReport(@PathVariable Long id) {

        ReviewReport report = service.findOne(id);

        if (report != null) {
            service.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
