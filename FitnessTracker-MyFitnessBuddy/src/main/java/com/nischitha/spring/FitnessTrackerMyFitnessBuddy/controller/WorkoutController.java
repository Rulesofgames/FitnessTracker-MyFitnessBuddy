package com.nischitha.spring.FitnessTrackerMyFitnessBuddy.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.entities.Exercise;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.entities.Sets;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.entities.Workout;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.repos.ExerciseRepository;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.repos.SetsRepository;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.repos.UserRepository;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.repos.WorkoutReopository;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.services.FitnessTrackerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class WorkoutController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	FitnessTrackerService fitnessTrackerServiceImpl;

	@Autowired
	UserRepository userRepo;

	@Autowired
	SetsRepository setsRepo;

	@Autowired
	WorkoutReopository workoutRepo;

	@Autowired
	ExerciseRepository exerciseRepo;

	@RequestMapping("addWorkout")
	public  String addWorkout(ModelMap modelmap,HttpSession session) {
		LOGGER.info("Inside addWorkout()");
		int userId=(int)session.getAttribute("userId");
		List<Workout> workoutList = workoutRepo.findByUserId(userId,Sort.by(new Sort.Order(Direction.DESC, "date"), new Sort.Order(Direction.DESC, "startTime")));
		modelmap.addAttribute("workoutList", workoutList);
		modelmap.addAttribute("exerciseList", exerciseRepo.findAll());
		return "AddWorkout";
	}

	@GetMapping("addSets")
	public String addSets(ModelMap modelmap) {
		LOGGER.info("Inside addSets()");
		List<Workout> list = workoutRepo.findAll();
		modelmap.addAttribute("list", list);
		return "addSets";
	}

	@PostMapping("saveWorkout")
	public String saveWorkout(@ModelAttribute("workout") Workout workout, ModelMap modelmap,HttpSession session) {
		LOGGER.info("Inside saveWorkout()");
		int userId=(int)session.getAttribute("userId");
		workout.setUser(userRepo.findById(userId).get());
		Workout savedWorkout = fitnessTrackerServiceImpl.saveWorkout(workout);
		LOGGER.info("Saved workout:"+workout);
		return "redirect:addWorkout";

	}	

	@PostMapping("saveEditSet")
	public String saveEditSet(@ModelAttribute("set") Sets set, @RequestParam("workoutId") Integer workoutId,
			@RequestParam("exerciseId") Integer exerciseId, ModelMap modelmap) {
		LOGGER.info("Inside saveEditSet()");
		LOGGER.info("Edit set to be saved for workout : " + workoutId);
		LOGGER.info("Exercsie to be added to set : " + exerciseRepo.findById(exerciseId).get());
		set.setWorkout(workoutRepo.findById(workoutId).get());
		set.setExercise(exerciseRepo.findById(exerciseId).get());
		Sets savedSet = setsRepo.save(set);
		LOGGER.info("Set saved succesfully");
		return "redirect:/viewWorkoutLog?id=" + workoutId;
	}

	@PostMapping("saveSet")
	public String saveSet(@ModelAttribute("Sets") Sets sets, ModelMap modelmap,
			@RequestParam("exerciseId") Integer exerciseId, @RequestParam("workoutId") Integer workoutId) {
		LOGGER.info("Inside saveSet()");
		Exercise exercise = exerciseRepo.findById(exerciseId).get();
		Workout workout = workoutRepo.findById(workoutId).get();
		sets.setWorkout(workout);
		sets.setExercise(exercise);
		Sets savedSet=setsRepo.save(sets);
		LOGGER.info("Setsaved succesfully");
		return "redirect:/viewWorkoutLog?id="+workoutId;
	}

	@GetMapping("/viewWorkoutLog")
	public String viewWorkoutLog(@RequestParam("id") Integer workoutId, ModelMap modelmap) {
		LOGGER.info("Inside viewWorkoutLog()");
		modelmap.addAttribute("exerciseList", exerciseRepo.findAll());
		modelmap.addAttribute("setsList", setsRepo.findAllByWorkoutId(workoutId,Sort.by(new Sort.Order(Direction.DESC, "exercise"))));
		modelmap.addAttribute("workoutId",workoutId);
		return "displayWorkoutLog";
	}

	@PostMapping("deleteWorkout")
	public String deleteWorkout(@RequestParam("id") Integer workoutId) {
		LOGGER.info("Inside deleteWorkout()");
		LOGGER.info("Workout to be deleted: "+workoutRepo.findById(workoutId).get());
		workoutRepo.deleteById(workoutId);
		return "redirect:addWorkout";
	}

	@PostMapping("deleteSet")
	public String deleteSet(@RequestParam("setId") Integer setId, @RequestParam("workoutId") Integer workoutId) {
		LOGGER.info("Inside delete Set");
		LOGGER.info("Set to be deleted: "+setsRepo.findById(setId).get());
		setsRepo.deleteById(setId);
		return "redirect:/viewWorkoutLog?id=" + workoutId;
	}

	@RequestMapping("deleteSet")
	public String deleteSet(@RequestParam("id") Integer setId) {
		int workoutId = setsRepo.findById(setId).get().getWorkout().getId();
		setsRepo.deleteById(setId);
		return "redirect:viewWorkoutLog?id=" + workoutId;
	}
	
	@GetMapping("summary")
	public @ResponseBody List<Workout> workoutSummary(HttpSession session){
		LOGGER.info("Inside workoutSummary()");
		int userId=(int)session.getAttribute("userId");
		List<Workout> list=workoutRepo.findByUserId(userId,Sort.by(new Sort.Order(Direction.DESC, "date"), new Sort.Order(Direction.DESC, "startTime")));
		return list;
	}
	
	@RequestMapping("displayHomePage")
	public String displayHomePage() {
		return "displayHomePage";
	}
	
	@GetMapping("logOut")
	public String DisplaySignInPage(HttpSession session) {
		LOGGER.info("Inside logout");
		if (session.getAttribute("userId") != null) {
		    session.removeAttribute("userId");
		}
		session.invalidate();
		return "redirect:SignInPage";
	}

}
