package com.ISS.Booking_iss_tim21.model;

enum NotificationType{
    RESERVATION_REQUEST,
    RESERVATION_CANCELLATION,
    OWNER_REVIEW,
    ACCOMMODATION_REVIEW,
    RESERVATION_REQUEST_RESPONSE

}
public class Notification {

    //User recipient;
    NotificationType type;
    String message;

}
