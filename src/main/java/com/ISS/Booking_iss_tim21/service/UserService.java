package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.UserReport;
import com.ISS.Booking_iss_tim21.model.enumeration.UserType;
import com.ISS.Booking_iss_tim21.repository.UserReportRepository;
import com.ISS.Booking_iss_tim21.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

//    @Autowired
//    UserRepository repository;

    //this is temporary until the database is established
    private static List<User> mockedUsers = new ArrayList<>();

    static{
        mockedUsers.add(new User(7L, UserType.ADMIN, "admin@example.com", "admin123", "Admin", "User", "Country1", "City1", "Street1", "1234567890"));
        mockedUsers.add(new User(8L, UserType.GUEST, "guest@example.com", "guest123", "Guest", "User", "Country2", "City2", "Street2", "9876543210"));
        mockedUsers.add(new User(9L, UserType.OWNER, "owner@example.com", "owner123", "Owner", "User", "Country3", "City3", "Street3", "1112233444"));
        mockedUsers.add(new User(10L, UserType.ADMIN, "admin@example.com", "admin123", "Admin", "User", "Country1", "City1", "Street1", "1234567890"));
        mockedUsers.add(new User(11L, UserType.GUEST, "guest@example.com", "guest123", "Guest", "User", "Country2", "City2", "Street2", "9876543210"));
        mockedUsers.add(new User(12L, UserType.OWNER, "owner@example.com", "owner123", "Owner", "User", "Country3", "City3", "Street3", "1112233444"));
    }

    public List<User> getAll(){
//        return repository.findAll();
        return mockedUsers;
    }

    public User findOne(Long id) {
//        return repository.findById(id).orElseGet(null);
        for (User user : mockedUsers){
            if(user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    public void remove(Long id){
//        repository.deleteById(id);
        User user = findOne(id);
        if(user!=null){
            mockedUsers.remove(user);
        }
    }

    public User save(User user) {
//        return repository.save(user);
        mockedUsers.add(user);
        return findOne(user.getId());
    }

    public List<User> getTypeUsers(UserType type){
        List<User> users = new ArrayList<>();
        for (User user : getAll()){
            if(user.getType().equals(type)){
                users.add(user);
            }
        }
        return users;
    }
}
