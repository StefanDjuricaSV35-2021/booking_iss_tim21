package com.ISS.Booking_iss_tim21.controller;

import com.ISS.Booking_iss_tim21.dto.AccommodationDetailsDTO;
import com.ISS.Booking_iss_tim21.dto.NotificationDTO;
import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.Notification;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.enumeration.NotificationType;
import com.ISS.Booking_iss_tim21.service.NotificationService;
import com.ISS.Booking_iss_tim21.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth/notifications")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {

    @Autowired
    NotificationService notifService;

    @Autowired
    UserService userService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;



    @MessageMapping("/send")
    public ResponseEntity<?> sendNotification( NotificationDTO notificationDTO) {



        if(userService.isSubscribedTo(notificationDTO.getRecipientId(),notificationDTO.getType())) {
            simpMessagingTemplate.convertAndSendToUser(notificationDTO.getRecipientId().toString(), "/specific", notificationDTO.getMessage());
        }

        Notification notification=new Notification();
        notification.setType(notificationDTO.getType());
        notification.setMessage(notificationDTO.getMessage());
        notification.setRecipient(userService.findById(notificationDTO.getRecipientId()));

        notifService.save(notification);

        return new ResponseEntity<>(notificationDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<List<NotificationDTO>> getUserNotifications(@PathVariable Long userId) {

        //Accommodation accommodation = accommodationService.findOne(id);
        List<Notification> notifications= notifService.getUserNotification(userId);

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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
    public ResponseEntity<Void> deleteNotifications(@PathVariable Long id) {

        Notification notification = notifService.findOne(id);

        if (notification != null) {
            notifService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //    @RequestMapping(value="/notifications", method = RequestMethod.GET)
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_OWNER','ROLE_GUEST')")
//    public ResponseEntity<List<NotificationDTO>> getNotifications() {
//
//        List<Notification> notifications= notifService.getAll();
//
//        List<NotificationDTO> notificationsDTO = new ArrayList<>();
//        for (Notification n : notifications) {
//            notificationsDTO.add(new NotificationDTO(n));
//        }
//
//        return new ResponseEntity<>(notificationsDTO, HttpStatus.OK);
//    }

}
