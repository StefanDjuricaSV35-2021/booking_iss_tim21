package com.ISS.Booking_iss_tim21.model.enumeration;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

public enum Role {
    GUEST,
    ADMIN,
    OWNER
}

