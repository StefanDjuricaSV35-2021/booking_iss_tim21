package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.modelmapper.ModelMapper;

import java.util.HashSet;

@Getter
@AllArgsConstructor
public class AccommodationDetailsDTO {
    private Long id;
    private Long ownerId;
    private String name;
    private AccommodationType type;
    private int minGuests;
    private int maxGuests;
    private String description;
    private HashSet<Amenity> amenities;
    private HashSet<String> photos;
    private int daysForCancellation;

    public AccommodationDetailsDTO(Accommodation accommodation) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(accommodation, this);
    }
}
