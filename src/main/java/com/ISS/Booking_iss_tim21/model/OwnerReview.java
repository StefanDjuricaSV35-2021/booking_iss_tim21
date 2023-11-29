package com.ISS.Booking_iss_tim21.model;


import com.ISS.Booking_iss_tim21.dto.OwnerReviewDTO;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
public class OwnerReview {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_id")
    private User reviewed;

    @Column(name = "comment", nullable = true)
    private String comment;

    @Column(name = "rating", nullable = true)
    private int rating;



}

