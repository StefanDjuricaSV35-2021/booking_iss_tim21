package com.ISS.Booking_iss_tim21.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

enum NotificationType{
    RESERVATION_REQUEST,
    RESERVATION_CANCELLATION,
    OWNER_REVIEW,
    ACCOMMODATION_REVIEW,
    RESERVATION_REQUEST_RESPONSE

}
@Getter
@Setter
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User recipient;

    @Enumerated(EnumType.STRING)
    NotificationType type;

    @Column(name = "message", nullable = true)
    String message;

}
