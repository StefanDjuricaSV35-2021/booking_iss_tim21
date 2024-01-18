package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.enumeration.NotificationType;
import lombok.Data;

import java.util.List;

@Data
public class NotificationTypeUpdateRequest {
    private Long userId;
    private List<NotificationType> subscribedNotificationTypes;

}
