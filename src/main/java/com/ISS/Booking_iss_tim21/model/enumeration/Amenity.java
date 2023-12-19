package com.ISS.Booking_iss_tim21.model.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum Amenity {
    TV, WiFi, Parking, SmokeAlarm;


    public static List<Amenity> IntegersToAmenities(List<Integer> ints){

        List<Amenity> amenities=new ArrayList<>();

        for (Integer val : ints)
        {
            amenities.add(Amenity.values()[val]);
        }

        return amenities;

    }
}
