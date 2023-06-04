package com.springbootReactExample.springbootbackend.repository;

import com.springbootReactExample.springbootbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	List<User> findByLastName(String lastName);
	List<User> findByFirstName(String firstName);

	Optional<User> findByPassword(String password);
}
