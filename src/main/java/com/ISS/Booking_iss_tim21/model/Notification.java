package com.ISS.Booking_iss_tim21.model;

<<<<<<< Updated upstream
import com.ISS.Booking_iss_tim21.model.enumeration.NotificationType;

=======
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
>>>>>>> Stashed changes
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
