package com.ISS.Booking_iss_tim21.model.review;

import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.enumeration.ReviewType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Inheritance(strategy= InheritanceType.JOINED)
@DiscriminatorColumn(name="TYPE")
public class Review {


    @Id
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User reviewer;

    @Column(name = "comment", nullable = true)
    private String comment;

    @Column(name = "rating", nullable = true)
    private int rating;



}
