package com.nischitha.spring.FitnessTrackerMyFitnessBuddy;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.repos.ExerciseRepository;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.repos.SetsRepository;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.repos.UserRepository;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.repos.WorkoutReopository;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.services.FitnessTrackerService;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.services.FitnessTrackerServiceImpl;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.controller.WorkoutController;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.entities.*;
import jakarta.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WorkoutControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	UserRepository userRepo;

	@Mock
	SetsRepository setsRepo;

	@Mock
	WorkoutReopository workoutRepo;

	@Mock
	ExerciseRepository exerciseRepo;

	@Mock
	private HttpSession session;

	@Mock
	private ModelMap modelMap;

	@InjectMocks
	WorkoutController workoutController;

	@Mock
	FitnessTrackerServiceImpl fitnessTrackerServiceImpl;

	@Test
	@Order(1)
	void addWorkoutTest() {
		int userId = 1;
		List<Exercise> mockExerciseList = new ArrayList<>();
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.DESC, "date", "startTime"));
		List<Workout> workoutListData = new ArrayList<>(); // Replace with your test data
		Page<Workout> workoutPage = new PageImpl<>(workoutListData, pageable, workoutListData.size());
		when(workoutRepo.findByUserId(1, pageable)).thenReturn(workoutPage);
		when(exerciseRepo.findAll()).thenReturn(mockExerciseList);
		when(session.getAttribute("userId")).thenReturn(userId);
		String result = workoutController.addWorkout(modelMap, session, 0, 10);
		verify(exerciseRepo, times(1)).findAll();
		verify(workoutRepo, times(1)).findByUserId(userId, pageable);
		verify(session, times(1)).getAttribute("userId");
		verify(modelMap, times(1)).addAttribute("workoutList", workoutPage);
		verify(modelMap, times(1)).addAttribute("exerciseList", mockExerciseList);
		assertNotNull(result);
	}

	@Test
	@Order(2)
	void addSetsTest() throws Exception {
		List<Workout> mockWorkoutList = new ArrayList<>();
		when(workoutRepo.findAll()).thenReturn(mockWorkoutList);
		String result = workoutController.addSets(modelMap);
		verify(workoutRepo, times(1)).findAll();
		verify(modelMap, times(1)).addAttribute("list", mockWorkoutList);
		assertNotNull(result);
		assertEquals(result, "addSets");
	}

	@Test
	@Order(3)
	void saveWorkoutTest() {
		int userId = 1;
		Workout workout = new Workout();
		User user = new User();
		when(session.getAttribute("userId")).thenReturn(userId);
		when(userRepo.findById(userId)).thenReturn(Optional.ofNullable(user));
		when(fitnessTrackerServiceImpl.saveWorkout(workout)).thenReturn(workout);
		String result = workoutController.saveWorkout(workout, modelMap, session);
		verify(session, times(1)).getAttribute("userId");
		verify(fitnessTrackerServiceImpl, times(1)).saveWorkout(workout);
		assertNotNull(result);
		assertEquals(result, "redirect:addWorkout");

	}

	@Test
	@Order(4)
	void saveEditSetTest() {
		Workout workout = new Workout();
		Sets set = new Sets();
		Sets savedSet = new Sets();
		Exercise exercise = new Exercise();
		set.setWorkout(workout);
		when(workoutRepo.findById(1)).thenReturn(Optional.ofNullable(workout));
		when(exerciseRepo.findById(1)).thenReturn(Optional.ofNullable(exercise));
		when(setsRepo.save(set)).thenReturn(savedSet);
		String result = workoutController.saveEditSet(set, 1, 1, modelMap);
		verify(workoutRepo, times(1)).findById(1);
		verify(exerciseRepo, times(2)).findById(1);
		verify(setsRepo, times(1)).save(set);
		assertNotNull(result);
		assertEquals(result, "redirect:/viewWorkoutLog?id=1");
	}

	@Test
	@Order(5)
	void saveSetTest() {
		Workout workout = new Workout();
		workout.setId(1);
		Exercise exercise = new Exercise();
		exercise.setId(1);
		Sets set = new Sets();
		set.setExercise(exercise);
		set.setWorkout(workout);
		Sets savedSet = new Sets();
		when(workoutRepo.findById(1)).thenReturn(Optional.ofNullable(workout));
		when(exerciseRepo.findById(1)).thenReturn(Optional.ofNullable(exercise));
		when(setsRepo.save(set)).thenReturn(savedSet);
		String result = workoutController.saveSet(set, modelMap, 1, 1);
		verify(workoutRepo, times(1)).findById(1);
		verify(exerciseRepo, times(1)).findById(1);
		verify(setsRepo, times(1)).save(set);
		assertNotNull(result);
		assertEquals(result, "redirect:/viewWorkoutLog?id=1");
	}

	@Test
	@Order(6)
	void viewWorkoutLogTest() {
		List<Exercise> exerciseList = new ArrayList<>();
		when(exerciseRepo.findAll()).thenReturn(exerciseList);
		String result = workoutController.viewWorkoutLog(1, modelMap, 0, 10);
		verify(modelMap, times(1)).addAttribute("exerciseList", exerciseList);
		verify(modelMap, times(1)).addAttribute("workoutId", 1);
		verify(modelMap, times(1)).addAttribute("exerciseList", exerciseList);
		verify(modelMap, times(1)).addAttribute("workoutId", 1);
		assertNotNull(result);
		assertEquals(result, "displayWorkoutLog");
	}

	@Test
	@Order(7)
	void deleteSetTest_WithoutWorkoutidArgument() {
		Sets set = new Sets();
		Workout workout = new Workout();
		workout.setId(1);
		set.setWorkout(workout);
		doNothing().when(setsRepo).deleteById(1);
		when(setsRepo.findById(1)).thenReturn(Optional.ofNullable(set));
		String result = workoutController.deleteSet(1);
		verify(setsRepo, times(1)).deleteById(1);
		assertNotNull(result);
		assertEquals(result, "redirect:viewWorkoutLog?id=1");
	}

	@Test
	@Order(8)
	void workoutSummaryTest() {
		int userId = 1;
		List<Workout> mockWorkoutList = new ArrayList<>();
		mockWorkoutList.add(new Workout());
		mockWorkoutList.add(new Workout());
		when(session.getAttribute("userId")).thenReturn(userId);
		when(workoutRepo.findByUserId(eq(userId),
				eq(Sort.by(new Sort.Order(Direction.DESC, "date"), new Sort.Order(Direction.DESC, "startTime")))))
				.thenReturn(mockWorkoutList);
		List<Workout> workoutList = workoutController.workoutSummary(session);
		verify(session, times(1)).getAttribute("userId");
		assertNotNull(workoutList);
		assertEquals(2, workoutList.size());
	}

	@Test
	@Order(9)
	void DisplaySignInPageTest_ValidUserSession() {
		int userId = 1;
		when(session.getAttribute("userId")).thenReturn(1);
		doNothing().when(session).invalidate();
		String result = workoutController.DisplaySignInPage(session);
		verify(session, times(1)).removeAttribute("userId");
		assertNotNull(result);
		assertEquals(result, "redirect:SignInPage");
	}

	@Test
	@Order(10)
	void DisplaySignInPageTest_NullUserSession() {
		int userId = 1;
		when(session.getAttribute("userId")).thenReturn(null);
		doNothing().when(session).invalidate();
		String result = workoutController.DisplaySignInPage(session);
		verify(session, never()).removeAttribute("userId");
		assertNotNull(result);
		assertEquals(result, "redirect:SignInPage");
	}

}
