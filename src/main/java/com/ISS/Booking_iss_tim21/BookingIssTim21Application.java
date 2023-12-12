package com.ISS.Booking_iss_tim21;

import com.ISS.Booking_iss_tim21.model.User;
import com.ISS.Booking_iss_tim21.model.enumeration.Role;
import com.ISS.Booking_iss_tim21.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;

@SpringBootApplication
public class BookingIssTim21Application implements CommandLineRunner {

//	@Autowired
//	private UserRepository userRepository;

	public static void main(String[] args) {

		SpringApplication.run(BookingIssTim21Application.class, args
		);
	}

	@Override
	public void run(String... args) throws Exception {
//		User admin = userRepository.findByRole(Role.ADMIN);
//		if(admin == null){
//			User user = new User();
//			user.setEmail("admin@gmail.com");
//			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
//			user.setName("Admin");
//			user.setSurname("Admin");
//			user.setCountry("AdminCountry");
//			user.setCity("AdminCity");
//			user.setStreet("AdminStreet");
//			user.setPhone("AdminPhoneNumber");
//			user.setEnabled(true);
//			user.setRole(Role.ADMIN);
//
//			userRepository.save(user);
//		}
	}
}
