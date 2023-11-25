package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.OwnerReview;

public class OwnerReviewDTO {

    private Integer id;

    UserDTO reviewer;

    UserDTO reviewed;

    String comment;

    int rating;

    public OwnerReviewDTO(OwnerReview review) {
        this.id=review.getId();
        this.reviewer = new UserDTO(review.getReviewer());
        this.reviewed= new UserDTO(review.getReviewed());
        this.comment=review.getComment();
        this.rating=review.getRating();
    }

    public Integer getId() {
        return id;
    }

    public UserDTO getReviewer() {
        return reviewer;
    }

    public UserDTO getReviewed() {
        return reviewed;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }
}
