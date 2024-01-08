package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.UserDTO;
import com.ISS.Booking_iss_tim21.dto.UserReportDTO;
import com.ISS.Booking_iss_tim21.model.UserReport;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.service.UserReportService;
import com.ISS.Booking_iss_tim21.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/reports/users")
public class UserReportController {

    @Autowired
    private UserReportService userReportService;

    @Autowired
    UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserReportDTO>> getUserReports() {

        List<UserReport> reports = userReportService.getAll();

        // convert reports to DTOs
        List<UserReportDTO> reportsDTO = new ArrayList<>();
        for (UserReport s : reports) {
            reportsDTO.add(new UserReportDTO(s));
        }

        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/guest/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_GUEST')")
    public ResponseEntity<List<UserDTO>> getGuestsOwners(@PathVariable Long id) {

        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<User> users = userService.getGuestsOwners(user);

        List<UserDTO> usersDTO = new ArrayList<>();
        for (User u : users) {
            usersDTO.add(new UserDTO(u));
        }

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/owner/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER')")
    public ResponseEntity<List<UserDTO>> getOwnersGuests(@PathVariable Long id) {

        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<User> users = userService.getOwnersGuests(user);

        List<UserDTO> usersDTO = new ArrayList<>();
        for (User u : users) {
            usersDTO.add(new UserDTO(u));
        }

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<UserReportDTO> createUserReport(@RequestBody UserReportDTO userReportDTO) {

        if (userReportDTO.getReportedId() == null || userReportDTO.getReporterId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User reporter = userService.findById(userReportDTO.getReporterId());
        User reported = userService.findById(userReportDTO.getReportedId());

        if (reporter == null || reported == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        UserReport report = new UserReport();
        report.setReported(reported);
        report.setReporter(reporter);
        report.setDescription(userReportDTO.getDescription());


        userReportService.save(report);

        return new ResponseEntity<>(new UserReportDTO(report), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUserReport(@PathVariable Long id) {

        UserReport report = userReportService.findOne(id);

        if (report != null) {
            userReportService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
