package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.Role;
import com.ISS.Booking_iss_tim21.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public Role findById(Long id) {
        return repository.findById(id).orElseGet(null);
    }

    public List<Role> findByName(String name) {
        return this.repository.findByName(name);
    }


}
