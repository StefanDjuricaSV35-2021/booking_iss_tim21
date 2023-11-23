package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.UserReportDTO;
import com.ISS.Booking_iss_tim21.service.UserReportService;
import com.ISS.Booking_iss_tim21.model.UserReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reports/user")
public class UserReportController {

    @Autowired
    private UserReportService service;

    @GetMapping
    public ResponseEntity<List<UserReportDTO>> getUserReports() {

        List<UserReport> reports = service.getAll();

        // convert reports to DTOs
        List<UserReportDTO> reportsDTO = new ArrayList<>();
        for (UserReport s : reports) {
            reportsDTO.add(new UserReportDTO(s));
        }

        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUserReport(@PathVariable Integer id) {

        UserReport report = service.findOne(id);

        if (report != null) {
            service.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
