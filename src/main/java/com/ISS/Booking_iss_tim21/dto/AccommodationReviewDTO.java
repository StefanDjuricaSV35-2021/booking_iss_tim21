package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.review.AccommodationReview;
import lombok.Data;

@Data
public class AccommodationReviewDTO {

    private Long id;

    Long reviewerId;

    Long accommodationId;
    Long timePosted;

    String comment;

    int rating;

    public AccommodationReviewDTO(AccommodationReview review) {
        this.id=review.getId();
        this.reviewerId = review.getReviewer().getId();
        this.accommodationId=review.getReviewed().getId();
        this.comment=review.getComment();
        this.rating=review.getRating();
        this.timePosted = review.getTimePosted();
    }
}
