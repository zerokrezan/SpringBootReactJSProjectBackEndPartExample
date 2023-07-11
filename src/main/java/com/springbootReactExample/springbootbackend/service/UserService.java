package com.springbootReactExample.springbootbackend.service;

import com.springbootReactExample.springbootbackend.exceptions.PasswordIsInUseException;
import com.springbootReactExample.springbootbackend.exceptions.UserAlreadyExistsException;
import com.springbootReactExample.springbootbackend.exceptions.UserDoesNotExistException;
import com.springbootReactExample.springbootbackend.model.SecurityUser;
import com.springbootReactExample.springbootbackend.model.User;
import com.springbootReactExample.springbootbackend.model.requests.RequestId;
import com.springbootReactExample.springbootbackend.model.requests.ResetPasswordRequest;
import com.springbootReactExample.springbootbackend.repository.UserRepository;
import com.springbootReactExample.springbootbackend.repository.ResetPasswordRequestRepository;
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
	@Autowired
	private final ResetPasswordRequestRepository resetPasswordRequestRepository;

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
		Optional<User> user2 = userRepository.findById(URLDecoder.decode(username, StandardCharsets.UTF_8));
		if (user2.isEmpty()){
			LOGGER.error(new UserDoesNotExistException().toString());
			throw new UserDoesNotExistException();
		}

		return new SecurityUser(user2.get());

	}

	//DONE: validate if newPassword is used from another user
	public void resetUsersPassword(String id,String requestTime ,String newPassword) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()){
			Optional<User> passwordUsed = userRepository.findByPassword(newPassword);
			if (!newPassword.isEmpty() && passwordUsed.isEmpty()) {
				LOGGER.info("reseting user's password: "+ id);
				user.get().setPassword(newPassword);
			}else {
				LOGGER.error(new PasswordIsInUseException().toString());
				LOGGER.error("deleting this request of type ResetPasswordRequest with id: "+ id + " and requestTime: "+ requestTime);
				Optional<ResetPasswordRequest> request =  resetPasswordRequestRepository.findById(new RequestId(id, requestTime));
				request.ifPresent(resetPasswordRequestRepository::delete);
				LOGGER.error("request of type ResetPasswordRequest with id: "+ id + " and requestTime: "+ requestTime + " has just been deleted!");
				throw new PasswordIsInUseException();
			}
			RequestId requestId = new RequestId(id, requestTime);
			Optional<ResetPasswordRequest> resetPasswordRequest = resetPasswordRequestRepository.findById(requestId);
			resetPasswordRequest.ifPresent(resetPasswordRequestRepository::delete);

			userRepository.saveAndFlush(user.get());
		}
	}

	//TODO: check before resetting password if newPassword != password -> Frontend
	public void requestPasswordReset(RequestId requestId, String newPassword) {
		LOGGER.info("user: "+ requestId.getUserId() + " is requesting PasswordRequest!");
		resetPasswordRequestRepository.save(new ResetPasswordRequest(requestId, newPassword));
		LOGGER.info("ResetPasswordRequest by user: "+ requestId.getUserId() + " just requested.");
		LOGGER.info("waiting for admin's action!");
	}

	//TODO: after refusing the request let user a notice
	public void refusePasswordReset(String id, String requestTime, String newPassword) {
		LOGGER.error("refusing this request of type ResetPasswordRequest with id: "+ id + " and requestTime: "+ requestTime);
		Optional<ResetPasswordRequest> request =  resetPasswordRequestRepository.findById(new RequestId(id, requestTime));
		request.ifPresent(resetPasswordRequestRepository::delete);
		LOGGER.error("request of type ResetPasswordRequest with id: "+ id + " and requestTime: "+ requestTime + " has just been deleted!");



	}
}
