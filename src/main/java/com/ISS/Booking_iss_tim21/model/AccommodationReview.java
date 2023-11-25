package com.ISS.Booking_iss_tim21.model;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
public class AccommodationReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User reviewer;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    Accommodation reviewed;
    
    @Column(name = "comment", nullable = true)
    private String comment;

    @Column(name = "rating", nullable = true)
    private int rating;

}

