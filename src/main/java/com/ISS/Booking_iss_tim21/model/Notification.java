package com.ISS.Booking_iss_tim21.model;


import com.ISS.Booking_iss_tim21.dto.NotificationDTO;
import com.ISS.Booking_iss_tim21.model.enumeration.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User recipient;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name = "message", nullable = true)
    private String message;

}
