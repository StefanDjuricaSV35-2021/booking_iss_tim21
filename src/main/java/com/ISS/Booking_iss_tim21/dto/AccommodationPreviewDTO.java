package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.enumeration.AccommodationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationPreviewDTO {
    private Long id;
    private String name;
    private String location;
    private String image;
    private Double price;

    public AccommodationPreviewDTO(Accommodation accommodation) {
        ModelMapper modelMapper = new ModelMapper();

        // Explicitly define the mappings
        PropertyMap<Accommodation, AccommodationPreviewDTO> propertyMap = new PropertyMap<Accommodation, AccommodationPreviewDTO>() {
            protected void configure() {
                map().setId(source.getId());
                map().setName(source.getName());
                map().setLocation(source.getLocation());

            }
        };

        modelMapper.addMappings(propertyMap);

        // Perform the mapping
        modelMapper.map(accommodation, this);
    }
}


