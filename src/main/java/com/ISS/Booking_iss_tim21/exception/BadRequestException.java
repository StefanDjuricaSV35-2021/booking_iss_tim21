package com.ISS.Booking_iss_tim21.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException() {}

    public BadRequestException(String message) {
        super(message);
    }

}
