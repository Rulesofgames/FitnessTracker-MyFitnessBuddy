package com.nischitha.spring.FitnessTrackerMyFitnessBuddy.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);
	User findByEmail(String email);

}
