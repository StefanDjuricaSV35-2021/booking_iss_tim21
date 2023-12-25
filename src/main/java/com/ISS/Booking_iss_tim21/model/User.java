package com.ISS.Booking_iss_tim21.model;

import com.ISS.Booking_iss_tim21.dto.UserDTO;
import com.ISS.Booking_iss_tim21.model.enumeration.Role;
import com.ISS.Booking_iss_tim21.model.review.OwnerReview;
import com.ISS.Booking_iss_tim21.model.review.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="`USERS`")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "phone")
    private String phone;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "reviewed", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<OwnerReview> ownerReviews;

    @OneToMany(mappedBy = "reported", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<UserReport> userReports;

    @OneToMany(mappedBy = "reporter", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<UserReport> userReports2;

    @OneToMany(mappedBy = "reviewer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Accommodation> accommodations;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ReservationRequest> reservationRequests;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ReviewReport> reviewReports;

    @Transient
    private String jwt;

    public User(UserDTO userDTO) {
        this.Id = userDTO.getId();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.name = userDTO.getName();
        this.surname = userDTO.getSurname();
        this.country = userDTO.getCountry();
        this.city = userDTO.getCity();
        this.street = userDTO.getStreet();
        this.phone = userDTO.getPhone();
        this.role = userDTO.getRole();
        this.enabled = true;
    }


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
