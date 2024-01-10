package com.ISS.Booking_iss_tim21;

import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.enumeration.Role;
import com.ISS.Booking_iss_tim21.repository.UserRepository;
import com.ISS.Booking_iss_tim21.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BookingIssTim21Application implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReservationService reservationService;

	public static void main(String[] args) {

		SpringApplication.run(BookingIssTim21Application.class, args
		);
	}

	@Override
	public void run(String... args) throws Exception {

		reservationService.updateReservations();

//		User joe = new User();
//		joe.setEmail("joe@gmail.com");
//		joe.setPassword(new BCryptPasswordEncoder().encode("1234"));
//		joe.setName("joe");
//		joe.setSurname("joe");
//		joe.setCountry("joe");
//		joe.setCity("joe");
//		joe.setStreet("joe");
//		joe.setPhone("joe");
//		joe.setEnabled(true);
//		joe.setRole(Role.GUEST);
//
//		userRepository.save(joe);

		User admin = userRepository.findByRole(Role.ADMIN);
		if(admin == null){
			User user = new User();
			user.setEmail("admin@gmail.com");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setName("Admin");
			user.setSurname("Admin");
			user.setCountry("AdminCountry");
			user.setCity("AdminCity");
			user.setStreet("AdminStreet");
			user.setPhone("AdminPhoneNumber");
			user.setEnabled(true);
			user.setBlocked(false);
			user.setRole(Role.ADMIN);

			userRepository.save(user);
		}
	}
}
