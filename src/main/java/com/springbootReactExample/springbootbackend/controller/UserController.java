package com.springbootReactExample.springbootbackend.controller;

import com.springbootReactExample.springbootbackend.exceptions.PasswordIsInUseException;
import com.springbootReactExample.springbootbackend.exceptions.UserDoesNotExistException;
import com.springbootReactExample.springbootbackend.service.UserService;
import com.springbootReactExample.springbootbackend.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class UserController {
	private static final Logger LOGGER = LogManager.getLogger(UserController.class);
	@Autowired
	private final UserService userService;

	@GetMapping("users")
	public List<User> getUsers() {
		return this.userService.getUsers();
	}

	//TODO: password as requestParam for createUser() - method
	//TODO: password as requestParam for updateUser() - method

	@PostMapping("newUser")
	public void createUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("email") String email){
		try{
			this.userService.createUser(new User(firstName, lastName, email));
			//LOGGER.info("create new user: "+ );
		}catch (PasswordIsInUseException passwordIsInUseException){
			LOGGER.error(passwordIsInUseException);
		}
	}

	@DeleteMapping("users")
	public void deleteUser(@RequestParam("id") int id){
		long idLong = id;
		try{
			User user = userService.getUserById(id);
			LOGGER.info("Delete User: "+user.getId()+" "+user.getFirstName()+" "+user.getLastName()+" "+user.getEmail()+" ");
			this.userService.deleteUser(idLong);
		}catch (UserDoesNotExistException userDoesNotExistException){
			LOGGER.warn(userDoesNotExistException);
		}

	}

	@PutMapping("users")
	public void updateUser(@RequestParam("id") int id, @RequestParam("firstName") String firstName,
						   @RequestParam("lastName") String lastName, @RequestParam("email") String email ){
		long idLong = id;
		this.userService.updateUser(idLong, firstName, lastName, email);
	}

}
