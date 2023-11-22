package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

@Getter
@Setter
@AllArgsConstructor
public class AccommodationPreviewDTO {
    private Long id;
    private Long ownerId;
    private String name;
    private AccommodationType type;

    public AccommodationPreviewDTO(Accommodation accommodation) {
        ModelMapper modelMapper = new ModelMapper();

        // Explicitly define the mappings
        PropertyMap<Accommodation, AccommodationPreviewDTO> propertyMap = new PropertyMap<Accommodation, AccommodationPreviewDTO>() {
            protected void configure() {
                map().setId(source.getId());
                map().setOwnerId(source.getOwnerId());
                map().setName(source.getName());
                map().setType(source.getType());
            }
        };

        modelMapper.addMappings(propertyMap);

        // Perform the mapping
        modelMapper.map(accommodation, this);
    }
}
