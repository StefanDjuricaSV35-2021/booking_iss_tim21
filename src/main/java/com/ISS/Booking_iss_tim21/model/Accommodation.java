package com.ISS.Booking_iss_tim21.model;

import com.ISS.Booking_iss_tim21.dto.AccommodationDetailsDTO;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Entity
public class Accommodation {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Accommodation(AccommodationDetailsDTO accommodationDTO) {
        this.id = accommodationDTO.getId();
        this.ownerId = accommodationDTO.getOwnerId();
        this.name = accommodationDTO.getName();
        this.type = accommodationDTO.getType();
        this.minGuests = accommodationDTO.getMinGuests();
        this.maxGuests = accommodationDTO.getMaxGuests();
        this.description = accommodationDTO.getDescription();
        this.amenities = accommodationDTO.getAmenities();
        this.photos = accommodationDTO.getPhotos();
        this.daysForCancellation = accommodationDTO.getDaysForCancellation();
    }
}
