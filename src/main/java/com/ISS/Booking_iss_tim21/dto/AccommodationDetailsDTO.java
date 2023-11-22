package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import com.ISS.Booking_iss_tim21.model.enumeration.Amenity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.HashSet;

@Getter
@Setter
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

        // Explicitly define the mappings
        PropertyMap<Accommodation, AccommodationDetailsDTO> propertyMap = new PropertyMap<Accommodation, AccommodationDetailsDTO>() {
            protected void configure() {
                map().setId(source.getId());
                map().setOwnerId(source.getOwnerId());
                map().setName(source.getName());
                map().setType(source.getType());
                map().setMinGuests(source.getMinGuests());
                map().setMaxGuests(source.getMaxGuests());
                map().setDescription(source.getDescription());
                map().setAmenities(source.getAmenities());
                map().setPhotos(source.getPhotos());
                map().setDaysForCancellation(source.getDaysForCancellation());
            }
        };

        modelMapper.addMappings(propertyMap);

        // Perform the mapping
        modelMapper.map(accommodation, this);
    }
}
