package com.nischitha.spring.FitnessTrackerMyFitnessBuddy.services;

import java.util.List;
import java.util.Map;

import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.entities.Workout;

public interface FitnessTrackerService {

	public Map<String, String> checkPassword(String password);

	public Map<String, List<String>> findExerciseCategories();

	public Workout saveWorkout(Workout workout);

	public Map<String, String> generateGraphData(String category, String subCategory, String metric, Integer timeframe,
			Integer userId);

}
