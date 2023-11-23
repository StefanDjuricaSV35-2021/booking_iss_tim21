package com.ISS.Booking_iss_tim21.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReviewReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    AccommodationReview reported;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    Owner reporter;

    @Column(name = "description", nullable = true)
    String description;
}
