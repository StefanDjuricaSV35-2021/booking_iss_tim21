package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.review.AccommodationReview;

public class AccommodationReviewDTO {

    private Long id;

    Long reviewerId;

    Long accommodationId;

    String comment;

    int rating;

    public AccommodationReviewDTO(AccommodationReview review) {
        this.id=review.getId();
        this.reviewerId = review.getReviewer().getId();
        this.accommodationId=review.getReviewed().getId();
        this.comment=review.getComment();
        this.rating=review.getRating();
    }

    public Long getId() {
        return id;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }


    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }
}
