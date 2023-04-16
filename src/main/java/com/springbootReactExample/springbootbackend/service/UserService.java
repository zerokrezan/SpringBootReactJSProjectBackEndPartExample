package com.springbootReactExample.springbootbackend.service;

import com.springbootReactExample.springbootbackend.exceptions.PasswordIsInUseException;
import com.springbootReactExample.springbootbackend.exceptions.UserDoesNotExistException;
import com.springbootReactExample.springbootbackend.model.User;
import com.springbootReactExample.springbootbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
	@Autowired
	private final UserRepository userRepository;

	public void createUser(User user){
		if (userRepository.findByPassword(user.getPassword()).isPresent()) {
			throw new PasswordIsInUseException();
		} else {
			userRepository.save(user);
		}
	}

	public void deleteUser(long userId){
		userRepository.deleteById(userId);
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

	private void renameUsersEmail(User user, String email){
		user.setEmail(email);
	}

	public void updateUser(long userId, String firstName, String lastName, String email){
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()){
			if (!firstName.isEmpty())
				renameUsersFirstName(user.get(), firstName);
			if (!lastName.isEmpty())
				renameUsersLastName(user.get(), lastName);
			if (!email.isEmpty())
				renameUsersEmail(user.get(), email);
			userRepository.saveAndFlush(user.get());
		}
	}

	public void assignAdminRights(long userId){
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

	public User getUserById(long userId){
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()){
			return user.get();
		}else {
			throw new UserDoesNotExistException();
		}
	}

}
