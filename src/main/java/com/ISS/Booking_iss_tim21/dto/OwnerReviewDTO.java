package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.Owner;
import com.ISS.Booking_iss_tim21.model.OwnerReview;
import com.ISS.Booking_iss_tim21.model.User;

public class OwnerReviewDTO {

    private Integer id;

    GuestDTO reviewer;

    OwnerDTO reviewed;

    String comment;

    int rating;

    public OwnerReviewDTO(OwnerReview review) {
        this.id=review.getId();
        this.reviewer = new GuestDTO(review.getReviewer());
        this.reviewed= new OwnerDTO(review.getReviewed());
        this.comment=review.getComment();
        this.rating=review.getRating();
    }

    public Integer getId() {
        return id;
    }

    public GuestDTO getReviewer() {
        return reviewer;
    }

    public OwnerDTO getReviewed() {
        return reviewed;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }
}
