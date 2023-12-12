package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.AccommodationDetailsDTO;
import com.ISS.Booking_iss_tim21.dto.NotificationDTO;
import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.Notification;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.enumeration.NotificationType;
import com.ISS.Booking_iss_tim21.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/auth/notifications")
public class NotificationController {

    @Autowired
    NotificationService service;

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getNotifications() {

        List<Notification> notifications= service.getAll();

        List<NotificationDTO> notificationsDTO = new ArrayList<>();
        for (Notification n : notifications) {
            notificationsDTO.add(new NotificationDTO(n));
        }

        return new ResponseEntity<>(notificationsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<NotificationDTO>> getUserNotifications(@PathVariable Long id) {

        //Accommodation accommodation = accommodationService.findOne(id);
        List<Notification> notifications= service.getUserNotification(id);

        if (notifications == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<NotificationDTO> notificationDTOS=new ArrayList<NotificationDTO>();

        for (Notification n:notifications) {

            notificationDTOS.add(new NotificationDTO(n));

        }

        return new ResponseEntity<>(notificationDTOS, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {

        Notification course = service.findOne(id);

        if (course != null) {
            service.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
