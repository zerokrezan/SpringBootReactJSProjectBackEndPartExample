package com.springbootReactExample.springbootbackend;

import com.springbootReactExample.springbootbackend.controller.UserController;
import com.springbootReactExample.springbootbackend.exceptions.PasswordIsInUseException;
import com.springbootReactExample.springbootbackend.model.User;
import com.springbootReactExample.springbootbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@SpringBootApplication
public class SpringbootBackendApplication implements CommandLineRunner {
	@Autowired
	UserRepository userRepository;


	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Optional<User> adminUser = userRepository.findByPassword("13042001");
		if (adminUser.isPresent()){
			System.out.println("adminUser found from DB: " + adminUser.get().getFirstName() + " " + adminUser.get().getLastName()
					+ " " + adminUser.get().getEmail());

		}
		else {
			User newAdminUser = new User("Rezan Hüseyin", "Yilmaz", "rezanbahar@hotmail.de", "13042001");
			userRepository.save(newAdminUser);
			System.out.println("new adminUser created "+ newAdminUser.getFirstName() + " " + newAdminUser.getLastName()
					+ " " + newAdminUser.getEmail());

		}
	}
}
