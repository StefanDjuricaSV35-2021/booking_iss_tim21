package com.ISS.Booking_iss_tim21.dto;

import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long Id;

    private String email;
    private String password;
    private String name;
    private String surname;
    private String country;
    private String city;
    private String street;
    private String phone;
    private boolean enabled;
    private Role role;

    public UserDTO(User user) {
        ModelMapper modelMapper = new ModelMapper();

        PropertyMap<User, UserDTO> propertyMap = new PropertyMap<User, UserDTO>() {
            protected void configure() {
                map().setId(source.getId());
                map().setEmail(source.getEmail());
                map().setPassword(source.getPassword());
                map().setName(source.getName());
                map().setSurname(source.getSurname());
                map().setCountry(source.getCountry());
                map().setCity(source.getCity());
                map().setStreet(source.getStreet());
                map().setPhone(source.getPhone());
                map().setRole(source.getRole());
                map().setEnabled(source.isEnabled());

            }
        };

        modelMapper.addMappings(propertyMap);

        // Perform the mapping
        modelMapper.map(user, this);
    }
}
