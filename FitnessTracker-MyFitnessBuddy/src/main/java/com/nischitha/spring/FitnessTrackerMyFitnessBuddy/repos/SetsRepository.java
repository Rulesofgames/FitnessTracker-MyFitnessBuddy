package com.nischitha.spring.FitnessTrackerMyFitnessBuddy.repos;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.entities.Sets;

public interface SetsRepository extends JpaRepository<Sets, Integer> {
	

	@Query("from Sets where workout.id=:workoutId")
	public List<Sets> findAllByWorkoutId(@Param("workoutId")int workoutId,Sort sort);

}
