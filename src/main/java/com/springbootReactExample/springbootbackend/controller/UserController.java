package com.springbootReactExample.springbootbackend.controller;

import com.springbootReactExample.springbootbackend.service.UserService;
import com.springbootReactExample.springbootbackend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class UserController {
	@Autowired
	private final UserService userService;

	@GetMapping("users")
	public List<User> getUsers() {
		return this.userService.getUsers();
	}

	@PostMapping("newUser")
	public void createUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("email") String email){
		this.userService.createUser(new User(firstName, lastName, email));
	}

	@DeleteMapping("users")
	public void deleteUser(@RequestParam("id") int id){
		long idLong = id;
		//TODO: use logger instead of printing id's
		System.out.println(idLong);
		this.userService.deleteUser(idLong);
	}

	@PutMapping("users")
	public void updateUser(@RequestParam("id") int id, @RequestParam("firstName") String firstName,
						   @RequestParam("lastName") String lastName, @RequestParam("email") String email ){
		long idLong = id;
		this.userService.updateUser(idLong, firstName, lastName, email);
	}

}
