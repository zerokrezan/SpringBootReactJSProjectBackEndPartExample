package com.springbootReactExample.springbootbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

	private String firstName;

	private String lastName;

	@Id
	@Column(name = "email")
	private String id;

	private boolean adminRights = false;

	private String password;

	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = email;
		this.password = password;
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
