package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.AdminDTO;
import com.ISS.Booking_iss_tim21.model.Admin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/courses")
public class AdminController {

//    @Autowired
//    private AdminService adminService;

    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAdmins() {

//        List<Admin> admins = adminService.findAll();
        List<Admin> admins = new ArrayList<>();

        // convert courses to DTOs
        List<AdminDTO> adminsDTO = new ArrayList<>();
        for (Admin s : admins) {
            adminsDTO.add(new AdminDTO(s));
        }

        return new ResponseEntity<>(adminsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AdminDTO> getAdmin(@PathVariable Integer id) {
//        Admin admin = adminService.findOne(id);
        Admin admin = new Admin();

        // course must exist
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AdminDTO(admin), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AdminDTO> saveAdmin(@RequestBody AdminDTO adminDTO) {
        Admin admin = new Admin();

        admin.setEmail(adminDTO.getEmail());
        admin.setPassword(adminDTO.getPassword());
        admin.setName(adminDTO.getName());
        admin.setSurname(adminDTO.getSurname());
        admin.setCountry(adminDTO.getCountry());
        admin.setCity(adminDTO.getCity());
        admin.setStreet(adminDTO.getStreet());
        admin.setPhone(adminDTO.getPhone());

//        admin = adminService.save(admin);
        return new ResponseEntity<>(new AdminDTO(admin), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<AdminDTO> updateAdmin(@RequestBody AdminDTO adminDTO) {
        // a course must exist
//        Admin admin = adminService.findOne(adminDTO.getUserId());
        Admin admin = new Admin();

        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        admin.setEmail(adminDTO.getEmail());
        admin.setPassword(adminDTO.getPassword());
        admin.setName(adminDTO.getName());
        admin.setSurname(adminDTO.getSurname());
        admin.setCountry(adminDTO.getCountry());
        admin.setCity(adminDTO.getCity());
        admin.setStreet(adminDTO.getStreet());
        admin.setPhone(adminDTO.getPhone());

//        admin = adminService.save(admin);
        return new ResponseEntity<>(new AdminDTO(admin), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Integer id) {
//        Admin course = adminService.findOne(id);
        Admin admin = new Admin();

        if (admin != null) {
//            adminService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

