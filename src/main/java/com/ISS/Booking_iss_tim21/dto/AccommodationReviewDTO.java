package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.AccommodationReview;
import com.ISS.Booking_iss_tim21.model.OwnerReview;

public class AccommodationReviewDTO {

    private Long id;

    Long reviewerId;

    String comment;

    int rating;

    public AccommodationReviewDTO(AccommodationReview review) {
        this.id=review.getId();
        this.reviewerId = review.getReviewer().getId();
        this.comment=review.getComment();
        this.rating=review.getRating();
    }

    public Long getId() {
        return id;
    }

    public Long getReviewer() {
        return reviewerId;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }
}
