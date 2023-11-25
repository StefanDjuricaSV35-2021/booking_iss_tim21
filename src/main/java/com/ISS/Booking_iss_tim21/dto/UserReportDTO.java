package com.ISS.Booking_iss_tim21.dto;


import com.ISS.Booking_iss_tim21.model.UserReport;

public class UserReportDTO {

        Integer id;
        UserDTO reported;
        UserDTO reporter;
        String description;

        public UserReportDTO(UserReport report) {
            this.id=report.getId();
            this.reported=new UserDTO(report.getReported());
            this.reporter=new UserDTO(report.getReported());
            this.description=report.getDescription();

        }
    }

