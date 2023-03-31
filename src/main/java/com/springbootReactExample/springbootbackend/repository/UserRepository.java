package com.springbootReactExample.springbootbackend.repository;

import com.springbootReactExample.springbootbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByLastName(String firstName);
}
