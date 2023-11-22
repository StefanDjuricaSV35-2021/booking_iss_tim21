package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.Admin;
import com.ISS.Booking_iss_tim21.model.FavoriteAccommodation;
import com.ISS.Booking_iss_tim21.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

@Getter
@AllArgsConstructor
@Setter
public class FavoriteAccommodationDTO {
    private Long favoriteAccommodationId;

    private Long accommodationId;
    private Long userId;

    public FavoriteAccommodationDTO(FavoriteAccommodation favoriteAccommodation) {
        ModelMapper modelMapper = new ModelMapper();

        // Explicitly define the mappings
        PropertyMap<FavoriteAccommodation, FavoriteAccommodationDTO> propertyMap = new PropertyMap<FavoriteAccommodation, FavoriteAccommodationDTO>() {
            protected void configure() {
                map().setFavoriteAccommodationId(source.getFavoriteAccommodationId());
                map().setAccommodationId(source.getAccommodationId());
                map().setUserId(source.getUserId());
            }
        };

        modelMapper.addMappings(propertyMap);

        // Perform the mapping
        modelMapper.map(favoriteAccommodation, this);
    }
}
