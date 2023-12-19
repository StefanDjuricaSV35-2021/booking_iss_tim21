package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ISS.Booking_iss_tim21.utility.ImageManipulationTools.ImagePathSetToBase64;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDetailsDTO {
    private Long id;
    private Long ownerId;
    private String name;
    private AccommodationType type;
    private int minGuests;
    private int maxGuests;
    private String description;
    private List<Amenity> amenities;
    private List<String> photos;
    private int daysForCancellation;
    private boolean enabled;
    private boolean perNight;
    private String location;
    private String[] dates;

    public AccommodationDetailsDTO(Accommodation accommodation) {
        setId(accommodation.getId());
        setOwnerId(accommodation.getOwner().getId());
        setName(accommodation.getName());
        setType(accommodation.getType());
        setMinGuests(accommodation.getMinGuests());
        setMaxGuests(accommodation.getMaxGuests());
        setDescription(accommodation.getDescription());
        setAmenities(accommodation.getAmenities());
        setPhotos(new ArrayList<>(ImagePathSetToBase64(new HashSet<>(accommodation.getPhotos()))));
        setDaysForCancellation(accommodation.getDaysForCancellation());
        setLocation(accommodation.getLocation());
    }
}
