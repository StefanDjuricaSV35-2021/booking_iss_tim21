package com.ISS.Booking_iss_tim21.model;

enum ReviewType{
    Accommodation,
    Owner
}
public class Review {

    ReviewType type;

    //User reviewer;
    String comment;
    int rating;

}
