package com.ISS.Booking_iss_tim21.model.review;

import com.ISS.Booking_iss_tim21.model.ReservationRequest;
import com.ISS.Booking_iss_tim21.model.ReviewReport;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.enumeration.ReviewType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Inheritance
@Entity
@DiscriminatorColumn(name="TYPE")
public class Review {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User reviewer;

    @Column(name = "comment", nullable = true)
    private String comment;

    @Column(name = "rating", nullable = true)
    private int rating;

    @Column(name = "timePosted", nullable = true)
    private Long timePosted;

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ReviewReport> reviewReports;

}
