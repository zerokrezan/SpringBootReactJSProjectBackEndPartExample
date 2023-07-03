package com.springbootReactExample.springbootbackend.controller;

import com.springbootReactExample.springbootbackend.exceptions.UserDoesNotExistException;
import com.springbootReactExample.springbootbackend.model.User;
import com.springbootReactExample.springbootbackend.model.requests.Request;
import com.springbootReactExample.springbootbackend.model.requests.RequestId;
import com.springbootReactExample.springbootbackend.service.RequestService;
import com.springbootReactExample.springbootbackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
//TODO: add Logs for each endpoint request for a better overview and trace
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class UserController {
	private static final Logger LOGGER = LogManager.getLogger(UserController.class);
	@Autowired
	private final UserService userService;
	@Autowired
	private final RequestService requestService;

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("users")
	public List<User> getUsers() {
		return this.userService.getUsers();
	}

	//DONE: password as requestParam for createUser() - method
	//DONE: requestPassword method for admin
	//TODO: resetUsersPasswordRequest method for user to admin
	//TODO: resetUsersMail method for user to admin

	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Perform any desired logging or additional actions here
		if (authentication != null) {
			String username = authentication.getName();
			LOGGER.info("User logged out: "+ username);
		}

		// Logout the user using the SecurityContextLogoutHandler
		new SecurityContextLogoutHandler().logout(request, response, authentication);

		// Optionally, redirect to the login page or any other desired page
		// response.sendRedirect();
	}
	@PreAuthorize("hasRole('ADMIN')")
	//@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("newUser")
	public void createUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("id") String id,
						   @RequestParam("password") String password){
		try{
			LOGGER.info(firstName +" " +lastName+" " + id+" " + password);
			this.userService.createUser(new User(firstName, lastName, id, password));
		}catch (Exception exception){
			LOGGER.error(exception);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("users")
	public void deleteUser(@RequestParam("id") String id){
		try{
			User user = userService.getUserById(id);
			LOGGER.info("Delete User: "+" "+user.getFirstName()+" "+user.getLastName()+" "+user.getId()+" ");
			this.userService.deleteUser(user.getId());
		}catch (UserDoesNotExistException userDoesNotExistException){
			LOGGER.warn(userDoesNotExistException);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("users")
	public void updateUser(@RequestParam("firstName") String firstName,
						   @RequestParam("lastName") String lastName, @RequestParam("id") String id ){
		this.userService.updateUser(firstName, lastName, id);
	}

	/**
	 * admin accepts reset-password request
	 * @param id
	 * @param newPassword
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("users/reset-password/{id}/{requestTime}/{newPassword}")
	public void resetUsersPassword(@PathVariable("id") String id, @PathVariable("requestTime") String requestTime,@PathVariable("newPassword") String newPassword){
		this.userService.resetUsersPassword(id,requestTime,newPassword);
	}

	/**
	 * admin refuses reset-pasword request
	 * @return
	 * @param
	 */
	//TODO:
	/*@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("users/{id}/{requestTime}/reset-password")
	public void refuseResetPasswordRequest(@RequestParam("id")) {
		return this.requestService.getRequests();
	}*/

	@PreAuthorize("hasRole('USER')")
	@PostMapping("requestPasswordReset")
	public void requestPasswordReset(@RequestParam("id") String id, @RequestParam("newPassword") String newPassword){
		//DONE: own request table/entitiy in DB -> no assign needed to users class because only the admin has access to requests table
		this.userService.requestPasswordReset(new RequestId(id, LocalDateTime.now().toString()));
	}

	//TODO: save request/s to admin's dashboard -> wait until admin accepty or defuses the request
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("requests")
	public <T extends Request> List<T> getRequests() {
		return this.requestService.getRequests();
	}

}
