package com.ISS.Booking_iss_tim21.repository;

import com.ISS.Booking_iss_tim21.model.Role;
import com.ISS.Booking_iss_tim21.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select r from Role r where r.name = ?1")
    public List<Role> findByName(String name);
}