package com.ISS.Booking_iss_tim21.dto;


import com.ISS.Booking_iss_tim21.model.*;

import javax.persistence.*;

public class ReviewReportDTO {


    private Long id;
    Long reportedAptId;
    Long reporterId;
    String description;

    public ReviewReportDTO(ReviewReport report) {
        this.id=report.getId();
        this.reportedAptId=report.getReported().getId();
        this.reporterId=report.getReporter().getId();
        this.description=report.getDescription();

    }

    public Long getId() {
        return id;
    }

    public Long getReportedAptId() {
        return reportedAptId;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public String getDescription() {
        return description;
    }
}

