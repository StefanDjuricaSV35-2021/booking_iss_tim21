package com.ISS.Booking_iss_tim21.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class OwnerReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    Guest reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    Owner reviewed;

    @Column(name = "comment", nullable = true)
    String comment;

    @Column(name = "rating", nullable = true)
    int rating;

}

