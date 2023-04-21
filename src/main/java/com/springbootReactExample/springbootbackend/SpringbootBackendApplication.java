package com.springbootReactExample.springbootbackend;

import com.springbootReactExample.springbootbackend.model.User;
import com.springbootReactExample.springbootbackend.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class SpringbootBackendApplication implements CommandLineRunner {
	private static final Logger LOGGER = LogManager.getLogger(SpringbootBackendApplication.class);
	@Autowired
	UserRepository userRepository;


	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}



	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			if (!userRepository.findByPassword("password").isPresent()){
				userRepository.save(new User("useremail@hotmail.de",("password")));
				LOGGER.info("new user created: "+ "useremail@hotmail.de ");
				return;
			}
			LOGGER.info("\"user already exists: \"+ \"useremail@hotmail.de \"");
		};
	}

	@Override
	public void run(String... args) throws Exception {
		Optional<User> adminUser = userRepository.findByPassword("123456");
		if (adminUser.isPresent()){
			System.out.println("adminUser found from DB: " + adminUser.get().getFirstName() + " " + adminUser.get().getLastName()
					+ " " + adminUser.get().getEmail());

		}
		else {
			User newAdminUser = new User("Rezan Hüseyin", "Yilmaz", "rezanbahar@hotmail.de", "123456");
			newAdminUser.setAdminRights(true);
			userRepository.save(newAdminUser);
			System.out.println("new adminUser created "+ newAdminUser.getFirstName() + " " + newAdminUser.getLastName()
					+ " " + newAdminUser.getEmail());

		}
	}
}
