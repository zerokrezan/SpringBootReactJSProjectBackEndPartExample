package com.springbootReactExample.springbootbackend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String firstName;

	private String lastName;

	//TODO: email als unique übergeben!
	private String email;

	private boolean adminRights = false;

	//TODO: password als unique übergeben!
	private String password;
	public User() {

	}

	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public User(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public User(String email, String password){
		this.email = email;
		this.password = password;
	}

	public User(String email, String password, boolean adminRights){
		this.email = email;
		this.password = password;
		this.adminRights = adminRights;
	}
}
