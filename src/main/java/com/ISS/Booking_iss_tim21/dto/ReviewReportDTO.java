package com.ISS.Booking_iss_tim21.dto;


import com.ISS.Booking_iss_tim21.model.*;

import javax.persistence.*;

public class ReviewReportDTO {


    private Long id;
    Long reportedReviewId;
    Long reporterId;
    String description;

    public ReviewReportDTO(ReviewReport report) {
        this.id=report.getId();
        this.reportedReviewId=report.getReported().getId();
        this.reporterId=report.getReporter().getId();
        this.description=report.getDescription();

    }

    public Long getId() {
        return id;
    }

    public Long getReportedReviewId() {
        return reportedReviewId;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public String getDescription() {
        return description;
    }
}

