package com.ISS.Booking_iss_tim21.dto;


import com.ISS.Booking_iss_tim21.model.UserReport;

public class UserReportDTO {

        Long id;
        Long reportedId;
        Long reporterId;
        String description;

        public UserReportDTO(UserReport report) {
            this.id=report.getId();
            this.reportedId=report.getReported().getId();
            this.reporterId=report.getReporter().getId();
            this.description=report.getDescription();

        }

    public Long getId() {
        return id;
    }

    public Long getReportedId() {
        return reportedId;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public String getDescription() {
        return description;
    }
}

