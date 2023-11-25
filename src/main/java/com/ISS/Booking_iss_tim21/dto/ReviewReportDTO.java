package com.ISS.Booking_iss_tim21.dto;


import com.ISS.Booking_iss_tim21.model.*;

import javax.persistence.*;

public class ReviewReportDTO {


    private Integer id;
    AccommodationReviewDTO reported;
    UserDTO reporter;
    String description;

    public ReviewReportDTO(ReviewReport report) {
        this.id=report.getId();
        this.reported=new AccommodationReviewDTO(report.getReported());
        this.reporter=new UserDTO(report.getReporter());
        this.description=report.getDescription();

    }
}

