package com.nischitha.spring.FitnessTrackerMyFitnessBuddy.repos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.entities.Sets;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.entities.Workout;

public interface SetsRepository extends JpaRepository<Sets, Integer>, PagingAndSortingRepository<Sets, Integer> {

	public Page<Sets> findByWorkoutId(int workoutId, Pageable pageable);

}
