package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.UserReport;
import com.ISS.Booking_iss_tim21.model.enumeration.UserType;
import com.ISS.Booking_iss_tim21.repository.UserReportRepository;
import com.ISS.Booking_iss_tim21.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService  {

    @Autowired
    UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findOne(Long id) {
        return repository.findById(id).orElseGet(null);
    }

    public void remove(Long id){
        repository.deleteById(id);
    }

    public User save(User user) {
//        mozda treba da se enkodira password
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.repository.save(user);
    }

    public List<User> getTypeUsers(UserType type){
        List<User> users = new ArrayList<>();
        for (User user : findAll()){
            if(user.getType().equals(type)){
                users.add(user);
            }
        }
        return users;
    }
}
