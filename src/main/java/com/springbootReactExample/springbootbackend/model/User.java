package com.springbootReactExample.springbootbackend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

	private String firstName;

	private String lastName;

	//DONE:email als unique übergeben!
	@Id
	@Column(name = "email")
	private String id;

	private boolean adminRights = false;

	private String password;
	public User() {

	}

	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = email;
		this.password = password;
	}

	public User(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = email;
	}

	public User(String email, String password){
		this.id = email;
		this.password = password;
	}

	public User(String email, String password, boolean adminRights){
		this.id = email;
		this.password = password;
		this.adminRights = adminRights;
	}
}
