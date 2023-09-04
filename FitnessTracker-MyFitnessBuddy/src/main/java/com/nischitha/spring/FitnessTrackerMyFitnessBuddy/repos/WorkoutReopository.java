package com.nischitha.spring.FitnessTrackerMyFitnessBuddy.repos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.entities.Workout;

public interface WorkoutReopository
		extends JpaRepository<Workout, Integer>{

	Page<Workout> findByUserId(Integer userId, Pageable pageable);

	List<Workout> findByUserId(Integer userId, Sort sort);

	@Query(value = "select date,SUM(duration) from workout w WHERE DATEDIFF(CURDATE(), `date`)<=:timeframe AND w.date<=CURDATE() AND w.user_id=:userId GROUP BY date", nativeQuery = true)
	public List<Object[]> findDuration(@Param("timeframe") Integer timeframe, @Param("userId") Integer userId);

	@Query(value = "SELECT date, COUNT(*) as totalSets\r\n" + "FROM workout w\r\n"
			+ "JOIN sets s ON w.id = s.workout_id\r\n"
			+ "where  DATEDIFF(CURDATE(), `date`) <=:timeframe AND w.date<=CURDATE() AND w.user_id=:userId \r\n"
			+ "GROUP BY date;", nativeQuery = true)
	public List<Object[]> findTotalSets(@Param("timeframe") Integer timeframe, @Param("userId") Integer userId);

	@Query(value = "SELECT w.date, SUM(CASE :metric WHEN 'weight' THEN s.weight WHEN 'minutes' THEN s.minutes WHEN 'distance' THEN s.distance WHEN 'kcal' THEN s.kcal WHEN 'reps' THEN s.reps END) "
			+ " FROM workout w JOIN sets s ON w.id = s.workout_id WHERE  DATEDIFF(CURDATE(), `date`) <=:timeframe AND w.date<=CURDATE() AND w.user_id=:userId GROUP BY date", nativeQuery = true)
	public List<Object[]> findTotal(@Param("metric") String metric, @Param("timeframe") Integer timeframe,
			@Param("userId") Integer userId);

	@Query(value = "SELECT w.date,AVG(body_weight) from workout w"
			+ " WHERE  DATEDIFF(CURDATE(), `date`) <=:timeframe AND w.date<=CURDATE() AND w.user_id=:userId"
			+ " GROUP BY w.date", nativeQuery = true)
	public List<Object[]> findBodyWeight(@Param("timeframe") Integer timeframe, @Param("userId") Integer userId);

	@Query(value = "SELECT w.date,SUM(CASE :metric WHEN 'weight' THEN s.weight WHEN 'minutes' THEN s.minutes WHEN 'distance' THEN s.distance WHEN 'kcal' THEN s.kcal WHEN 'reps' THEN s.reps END) from workout w join sets s on s.workout_id=w.id join exercise e on s.exercise_id=e.id "
			+ " WHERE  w.user_id=:userId AND e.exercise_category=:category AND DATEDIFF(CURDATE(), `date`) <=:timeframe AND w.date<=curdate() "
			+ " GROUP BY w.date;", nativeQuery = true)
	public List<Object[]> findCategoryTotal(@Param("category") String category, @Param("metric") String metric,
			@Param("timeframe") Integer timeframe, @Param("userId") Integer userId);

	@Query(value = "SELECT w.date,COUNT(*) from workout w join sets s on s.workout_id=w.id join exercise e on s.exercise_id=e.id "
			+ " WHERE  w.user_id=:userId AND e.exercise_category=:category AND DATEDIFF(CURDATE(), `date`) <=:timeframe AND w.date<=curdate() "
			+ " GROUP BY w.date;", nativeQuery = true)
	public List<Object[]> findCategorySets(@Param("category") String category, @Param("timeframe") Integer timeframe,
			@Param("userId") Integer userId);

	@Query(value = "SELECT w.date,COUNT(*) from workout w join sets s on s.workout_id=w.id join exercise e on s.exercise_id=e.id "
			+ " WHERE  w.user_id=:userId and e.exercise_name=:subCategory and DATEDIFF(CURDATE(), `date`) <=:timeframe AND w.date<=curdate() "
			+ " GROUP BY w.date;", nativeQuery = true)
	public List<Object[]> findSubCategorySets(@Param("subCategory") String subCategory,
			@Param("timeframe") Integer timeframe, @Param("userId") Integer userId);

	@Query(value = "SELECT w.date,SUM(CASE :metric WHEN 'weight' THEN s.weight WHEN 'minutes' THEN s.minutes WHEN 'distance' THEN s.distance WHEN 'kcal' THEN s.kcal WHEN 'reps' THEN s.reps END) from workout w join sets s on s.workout_id=w.id join exercise e on s.exercise_id=e.id "
			+ "WHERE  w.user_id=:userId and e.exercise_name=:subCategory and DATEDIFF(CURDATE(), `date`) <=:timeframe AND w.date<=curdate() "
			+ "GROUP BY w.date;", nativeQuery = true)
	public List<Object[]> findSubCategoryTotal(@Param("subCategory") String subCategory, @Param("metric") String metric,
			@Param("timeframe") Integer timeframe, @Param("userId") Integer userId);


}
