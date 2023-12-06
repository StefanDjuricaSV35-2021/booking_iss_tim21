package com.ISS.Booking_iss_tim21.model;

import com.ISS.Booking_iss_tim21.dto.AccommodationDetailsDTO;
import com.ISS.Booking_iss_tim21.dto.UserDTO;
import com.ISS.Booking_iss_tim21.model.enumeration.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import jakarta.persistence.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="`users`")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private UserType type;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String country;
    private String city;
    private String street;
    private String phone;
    private boolean enabled;

    public User(UserDTO userDTO) {
        this.Id = userDTO.getId();
        this.type = userDTO.getType();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.name = userDTO.getName();
        this.surname = userDTO.getSurname();
        this.country = userDTO.getCountry();
        this.city = userDTO.getCity();
        this.street = userDTO.getStreet();
        this.phone = userDTO.getPhone();
        this.enabled = userDTO.isEnabled();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
