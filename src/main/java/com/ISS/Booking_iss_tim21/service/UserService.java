package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.dto.UserDTO;
import com.ISS.Booking_iss_tim21.model.enumeration.Role;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id).orElseGet(null);
    }

    public void remove(Long id){
        repository.deleteById(id);
    }

    public User save(UserDTO userDTO) {
        User u = new User(userDTO);

        u.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        u.setEmail(userDTO.getEmail());

        return this.repository.save(u);
    }
    public User findByEmail(String  email){
        return this.repository.findByEmail(email).orElseGet(null);
    }

    public UserDetailsService userDetailService(){
        return new UserDetailsService(){
            @Override
            public UserDetails loadUserByUsername(String username){
                return repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
}
