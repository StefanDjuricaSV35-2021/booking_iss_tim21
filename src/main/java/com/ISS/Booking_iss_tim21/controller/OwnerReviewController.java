package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.OwnerReviewDTO;
import com.ISS.Booking_iss_tim21.model.OwnerReview;
import com.ISS.Booking_iss_tim21.service.OwnerReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reviews/user")
public class OwnerReviewController {

    @Autowired
    private OwnerReviewService service;



    @GetMapping
    public ResponseEntity<List<OwnerReviewDTO>> getOwnerReviews() {

        List<OwnerReview> reviews = service.getAll();

        // convert reviews to DTOs
        List<OwnerReviewDTO> reviewsDTO = new ArrayList<>();
        for (OwnerReview s : reviews) {
            reviewsDTO.add(new OwnerReviewDTO(s));
        }

        return new ResponseEntity<>(reviewsDTO, HttpStatus.OK);
    }

  //@PostMapping(consumes = "application/json")
    //public ResponseEntity<OwnerReviewDTO> createExam(@RequestBody OwnerReviewDTO ownerReviewDTO) {

        // a new exam must have student and course defined
//        if (ownerReviewDTO.getReviewer() == null || ownerReviewDTO.getReviewed() == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }

       //Guest reviewer = guestService.findOne(ownerReviewDTO.getReviewer().getUserId());
        //Owner reviewed = ownerService.findOne(ownerReviewDTO.getReviewed().getUserId());

//        if (reviewer == null || reviewed == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        OwnerReview review = new OwnerReview();
//        exam.setDate(examDTO.getDate());
//        exam.setGrade(examDTO.getGrade());
//        exam.setStudent(student);
//        exam.setCourse(course);
//        course.addExam(exam);
//        student.addExam(exam);
//
//        exam = examService.save(exam);
//        return new ResponseEntity<>(new ExamDTO(exam), HttpStatus.CREATED);
    //}


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOwnerReview(@PathVariable Integer id) {

        OwnerReview review = service.findOne(id);

        if (review != null) {
            service.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
