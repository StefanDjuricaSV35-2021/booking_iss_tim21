package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

@Getter
@AllArgsConstructor
@Setter
public class UserDTO {
    private Long userId;

    private String email;
    private String password;
    private String name;
    private String surname;
    private String country;
    private String city;
    private String street;
    private String phone;

    public UserDTO(User user) {
        ModelMapper modelMapper = new ModelMapper();

        // Explicitly define the mappings
        PropertyMap<User, UserDTO> propertyMap = new PropertyMap<User, UserDTO>() {
            protected void configure() {
                map().setUserId(source.getUserId());
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
        modelMapper.map(user, this);
    }
}
