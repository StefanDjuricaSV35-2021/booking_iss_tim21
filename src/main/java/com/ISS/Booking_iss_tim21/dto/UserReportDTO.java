package com.ISS.Booking_iss_tim21.dto;


import com.ISS.Booking_iss_tim21.model.Guest;
import com.ISS.Booking_iss_tim21.model.Owner;
import com.ISS.Booking_iss_tim21.model.UserReport;

public class UserReportDTO {

        Integer id;
        GuestDTO reported;
        OwnerDTO reporter;
        String description;

        public UserReportDTO(UserReport report) {
            this.id=report.getId();
            this.reported=new GuestDTO((Guest) report.getReported());
            this.reporter=new OwnerDTO((Owner) report.getReported());
            this.description=report.getDescription();

        }
    }

