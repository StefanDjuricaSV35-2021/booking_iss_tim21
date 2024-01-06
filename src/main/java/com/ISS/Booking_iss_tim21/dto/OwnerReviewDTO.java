package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.review.OwnerReview;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OwnerReviewDTO {

    private Long id;

    private Long reviewerId;

    private Long reviewedId;
    private Long timePosted;

    String comment;

    int rating;

    public OwnerReviewDTO(OwnerReview review) {
        this.id=review.getId();
        this.reviewerId = review.getReviewer().getId();
        this.reviewedId= review.getReviewed().getId();
        this.comment=review.getComment();
        this.rating=review.getRating();
        this.timePosted=review.getTimePosted();
    }
}
