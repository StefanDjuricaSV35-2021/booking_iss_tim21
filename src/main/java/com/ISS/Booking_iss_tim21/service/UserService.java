package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.UserReport;
import com.ISS.Booking_iss_tim21.repository.UserReportRepository;
import com.ISS.Booking_iss_tim21.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public List<User> getAll(){
        return repository.findAll();
    }

    public User findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }

    public void remove(Long id){
        repository.deleteById(id);
    }

    public User save(User user) {
        return repository.save(user);
    }
}
