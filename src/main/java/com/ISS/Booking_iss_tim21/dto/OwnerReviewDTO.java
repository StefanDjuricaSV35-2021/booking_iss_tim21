package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.OwnerReview;

public class OwnerReviewDTO {

    private Long id;

    private Long reviewerId;

    private Long reviewedId;

    String comment;

    int rating;

    public OwnerReviewDTO(OwnerReview review) {
        this.id=review.getId();
        this.reviewerId = review.getReviewer().getId();
        this.reviewedId= review.getReviewed().getId();
        this.comment=review.getComment();
        this.rating=review.getRating();
    }

    public Long getId() {
        return id;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public Long getReviewedId() {
        return reviewedId;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }
}
