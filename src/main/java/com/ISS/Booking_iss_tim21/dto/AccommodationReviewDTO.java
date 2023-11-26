package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.AccommodationReview;
import com.ISS.Booking_iss_tim21.model.OwnerReview;

public class AccommodationReviewDTO {

    private Long id;

    UserDTO reviewer;

    String comment;

    int rating;

    public AccommodationReviewDTO(AccommodationReview review) {
        this.id=review.getId();
        this.reviewer = new UserDTO(review.getReviewer());
        this.comment=review.getComment();
        this.rating=review.getRating();
    }

    public Long getId() {
        return id;
    }

    public UserDTO getReviewer() {
        return reviewer;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }
}
