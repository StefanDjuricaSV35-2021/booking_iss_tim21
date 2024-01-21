package com.ISS.Booking_iss_tim21.service;

import com.ISS.Booking_iss_tim21.dto.UserDTO;
import com.ISS.Booking_iss_tim21.model.Accommodation;
import com.ISS.Booking_iss_tim21.model.Reservation;
import com.ISS.Booking_iss_tim21.model.enumeration.NotificationType;
import com.ISS.Booking_iss_tim21.model.enumeration.ReservationStatus;
import com.ISS.Booking_iss_tim21.model.enumeration.Role;
import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.repository.ReservationRepository;
import com.ISS.Booking_iss_tim21.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AccommodationService accommodationService;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void remove(Long id){
        User user = findById(id);
        repository.deleteById(user.getId());
    }

    public User save(UserDTO userDTO) {
        User u = new User(userDTO);

        return this.repository.save(u);
    }

    public User save(User user) {
        return this.repository.save(user);
    }

    public User findByEmail(String  email){
        return this.repository.findByEmail(email).orElse(null);
    }

    public boolean isSubscribedTo(Long id,NotificationType type){
        User user=findById(id);
        List<NotificationType> subscribedTypes=user.getSubscribedNotificationTypes();

        if(subscribedTypes.contains(type)){
            return true;
        }

        return false;
    }


    public UserDetailsService userDetailService(){
        return new UserDetailsService(){
            @Override
            public UserDetails loadUserByUsername(String username){
                return repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    public List<User> getGuestsOwners(User user){
        List<User> reportableOwners = new ArrayList<>();
        List<Reservation> usersReservations = reservationRepository.getUsersReservationsById(user.getId());

        for (Reservation r: usersReservations) {
            if(r.getStatus().equals(ReservationStatus.Finished) && !reportableOwners.contains(r.getAccommodation().getOwner())){
                reportableOwners.add(r.getAccommodation().getOwner());
            }
        }

        return reportableOwners;
    }

    public List<User> getOwnersGuests(User user) {
        List<User> reportableGuests = new ArrayList<>();
        List<Reservation> usersReservations = reservationRepository.getOwnersReservationsById(user.getId());

        for (Reservation r : usersReservations) {
            if (r.getStatus().equals(ReservationStatus.Finished) && !reportableGuests.contains(r.getUser())) {
                reportableGuests.add(r.getUser());
            }
        }

        return reportableGuests;
    }
}
