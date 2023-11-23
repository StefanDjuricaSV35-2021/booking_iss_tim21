package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.Guest;
import com.ISS.Booking_iss_tim21.model.Owner;
import com.ISS.Booking_iss_tim21.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

@Getter
@AllArgsConstructor
@Setter
public class OwnerDTO {
    private Long userId;

    private String email;
    private String password;
    private String name;
    private String surname;
    private String country;
    private String city;
    private String street;
    private String phone;

    public OwnerDTO(Owner owner) {
        ModelMapper modelMapper = new ModelMapper();

        // Explicitly define the mappings
        PropertyMap<Owner, OwnerDTO> propertyMap = new PropertyMap<Owner, OwnerDTO>() {
            protected void configure() {
                map().setUserId(source.getId());
                map().setEmail(source.getEmail());
                map().setPassword(source.getPassword());
                map().setName(source.getName());
                map().setSurname(source.getSurname());
                map().setCountry(source.getCountry());
                map().setCity(source.getCity());
                map().setStreet(source.getStreet());
                map().setPhone(source.getPhone());
            }
        };

        modelMapper.addMappings(propertyMap);

        // Perform the mapping
        modelMapper.map(owner, this);
    }
}
