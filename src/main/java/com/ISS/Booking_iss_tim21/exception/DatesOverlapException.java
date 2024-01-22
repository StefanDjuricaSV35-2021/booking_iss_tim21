package com.ISS.Booking_iss_tim21.exception;

public class DatesOverlapException extends Exception {
    public DatesOverlapException(String errorMessage) {
        super(errorMessage);
    }

}
