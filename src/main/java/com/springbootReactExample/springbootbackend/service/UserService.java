package com.springbootReactExample.springbootbackend.service;

import com.springbootReactExample.springbootbackend.exceptions.PasswordIsInUseException;
import com.springbootReactExample.springbootbackend.exceptions.UserAlreadyExistsException;
import com.springbootReactExample.springbootbackend.exceptions.UserDoesNotExistException;
import com.springbootReactExample.springbootbackend.model.SecurityUser;
import com.springbootReactExample.springbootbackend.model.User;
import com.springbootReactExample.springbootbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	private static final Logger LOGGER = LogManager.getLogger(UserService.class);
	@Autowired
	private final UserRepository userRepository;

	//DONE: check for email/id existing before creating new user
	//TODO: PasswordEncryption has to be done for DB storage
	public void createUser(User user){
		//BCryptPasswordEncoder becrypt = new BCryptPasswordEncoder();
		if (userRepository.findByPassword(user.getPassword()).isPresent()) {
			LOGGER.error("password: "+ user.getPassword()+ " is already in use");
			throw new PasswordIsInUseException();
		}
		else if (userRepository.findById(user.getId()).isPresent()){
			LOGGER.error("user with id: "+ user.getId()+ " already exists");
			throw new UserAlreadyExistsException();
		} else {
			userRepository.save(user);
		}
	}

	public void deleteUser(String id){
		userRepository.deleteById(id);
	}

	public List<User> getUsers(){
		Set<User> allUsers = new LinkedHashSet<>(userRepository.findAll());
		return allUsers.stream().toList();
	}

	private void renameUsersFirstName(User user, String firstName){
		user.setFirstName(firstName);
	}

	private void renameUsersLastName(User user, String lastName){
		user.setLastName(lastName);
	}

	public void updateUser(String firstName, String lastName, String id){
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()){
			if (!firstName.isEmpty())
				renameUsersFirstName(user.get(), firstName);
			if (!lastName.isEmpty())
				renameUsersLastName(user.get(), lastName);
			userRepository.saveAndFlush(user.get());
		}
	}

	public void assignAdminRights(String userId){
		Optional<User> user = userRepository.findById(userId);
		user.ifPresent(value -> value.setAdminRights(true));
	}

	public Set<User> getUserByFirstName(String firstName){
		Set<User> usersByFirstName = new LinkedHashSet<>();
		userRepository.findByFirstName(firstName).stream().map((usersByFirstName::add));
		return usersByFirstName;
	}

	public Set<User> getUserByLastName(String lastName){
		Set<User> usersByLastName = new HashSet<>();
		userRepository.findByLastName(lastName).stream().map((usersByLastName::add));
		return usersByLastName;
	}

	public User getUserById(String userId){
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()){
			return user.get();
		}else {
			throw new UserDoesNotExistException();
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String decodedEmail = URLDecoder.decode(username, StandardCharsets.UTF_8);
		if (username.isEmpty()) {
			throw new UserDoesNotExistException();
		}
		LOGGER.info("check if user with email/ID: " + URLDecoder.decode(username, StandardCharsets.UTF_8) + " exists");
		User user2 = userRepository.findById(URLDecoder.decode(username, StandardCharsets.UTF_8))
				.orElseThrow(UserDoesNotExistException::new);

		return new SecurityUser(user2);

	}

	public void resetUsersPassword(String id, String newPassword) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()){
			if (!newPassword.isEmpty()) {
				LOGGER.info("reseting user's password: "+ id);
				user.get().setPassword(newPassword);
			}
			userRepository.saveAndFlush(user.get());
		}
	}
}
