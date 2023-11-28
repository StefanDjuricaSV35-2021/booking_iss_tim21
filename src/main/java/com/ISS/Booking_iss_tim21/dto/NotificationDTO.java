package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.Notification;

public class NotificationDTO {

    private Long id;
    String message;

    public NotificationDTO(Notification notification) {
        this(notification.getId(), notification.getMessage());
    }


    public NotificationDTO(Long id, String message) {
        this.id = id;
        this.message = message;
    }


    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
