package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.dto.UserDTO;
import com.ISS.Booking_iss_tim21.model.Role;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  {

    @Autowired
    UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

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

		List<Role> roles = roleService.findByName("GUEST");
		u.setRoles(roles);

		return this.repository.save(u);
    }
    public User findByEmail(String  email){
        return this.repository.findByEmail(email);
    }

}
