package com.ISS.Booking_iss_tim21.dto;


import com.ISS.Booking_iss_tim21.model.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewReportDTO {


    private Long id;
    Long reportedReviewId;
    Long reporterId;

    public ReviewReportDTO(ReviewReport report) {
        this.id=report.getId();
        this.reportedReviewId=report.getReview().getId();
        this.reporterId=report.getUser().getId();
    }

}

