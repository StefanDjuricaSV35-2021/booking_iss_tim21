package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.AccommodationReview;
import com.ISS.Booking_iss_tim21.model.OwnerReview;

public class AccommodationReviewDTO {

    private Integer id;

    GuestDTO reviewer;

    String comment;

    int rating;

    public AccommodationReviewDTO(AccommodationReview review) {
        this.id=review.getId();
        this.reviewer = new GuestDTO(review.getReviewer());
        this.comment=review.getComment();
        this.rating=review.getRating();
    }

    public Integer getId() {
        return id;
    }

    public GuestDTO getReviewer() {
        return reviewer;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }
}
