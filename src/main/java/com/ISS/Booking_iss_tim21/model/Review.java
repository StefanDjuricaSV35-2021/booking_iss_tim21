package com.ISS.Booking_iss_tim21.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

enum ReviewType{
    Accommodation,
    Owner
}

@Getter
@Setter
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    ReviewType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User reviewer;

    @Column(name = "comment", nullable = true)
    String comment;

    @Column(name = "rating", nullable = true)
    int rating;

}

