package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.Notification;
import com.ISS.Booking_iss_tim21.model.enumeration.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    private Long id;
    NotificationType type;
    String message;
    Long recipientId;

    public NotificationDTO(Notification notification) {
        this(notification.getId(), notification.getMessage());
    }

    public NotificationDTO(Long id, String message) {
    }
}
