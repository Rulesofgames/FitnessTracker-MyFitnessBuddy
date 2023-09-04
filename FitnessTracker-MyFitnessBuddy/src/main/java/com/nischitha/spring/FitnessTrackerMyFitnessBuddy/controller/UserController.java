package com.nischitha.spring.FitnessTrackerMyFitnessBuddy.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.entities.User;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.repos.UserRepository;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.services.FitnessTrackerService;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.util.EmailUtil;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserRepository userRepo;

	@Autowired
	EmailUtil emailUtil;

	@Autowired
	FitnessTrackerService fitnessTrackerServiceImpl;

	@Autowired
	public UserController(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@RequestMapping("SignUpPage")
	public String displaySignUpPage() {
		return "displaySignUpPage";
	}

	@RequestMapping("SignInPage")
	public String displaySignInPage() {
		return "displaySignInPage";
	}

	@PostMapping("registerUser")
	public String registerUser(@ModelAttribute("user") User user, ModelMap modelmap) {
		LOGGER.info("Inside registerUser()");
		User savedUser = userRepo.save(user);
		if (savedUser == null) {
			LOGGER.info("User registration failed");
			modelmap.addAttribute("msg", "User registration failed.Please try again");
			return "displaySignUpPage";
		} else {
			modelmap.addAttribute("msg", "User registration success.Please log in to continue");
			try {
				emailUtil.sendEmail(user.getEmail(), "Start your fitness journey",
						user.getFirstName() + ", Welcome to MYFITNESSBUDYY");
			} catch (Exception e) {
				LOGGER.info("Email succesfully sent to first time registering user,Email sent to: " + user.getEmail());
			}
			LOGGER.info("User saved successfully" + savedUser);
			return "displaySignUpPage";
		}
	}

	@GetMapping("checkEmail")
	public @ResponseBody Map<String, Boolean> checkEmailExists(@RequestParam("email") String email) {
		LOGGER.info("Inside checkEmailExists()" + email);
		boolean exists = userRepo.existsByEmail(email);
		LOGGER.info("Does email id already exists(True or False)?" + exists);
		Map<String, Boolean> response = new HashMap<>();
		response.put("exists", exists);
		return response;
	}

	@GetMapping("validatePassword")
	public @ResponseBody Map<String, String> validatePassword(@RequestParam("password") String password) {
		LOGGER.info("Inside validatePassword()");
		LOGGER.info("Password to be validated is: " + password);
		return fitnessTrackerServiceImpl.checkPassword(password);
	}

	@PostMapping("checklogIn")
	public String checkLogIn(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session, ModelMap modelmap) {
		LOGGER.info("Inside CheckLogIn()");
		User user = userRepo.findByEmail(email);
		if (user == null || !user.getPassword().equals(password)) {
			LOGGER.info("LogIn Failure,Email is : " + email);
			modelmap.addAttribute("msg",
					"Username or password is incorrect.Please enter correct username and password");
			return "displaySignInPage";
		} else {
			LOGGER.info("LogIn Sucessfull,Logged in user email is :" + email);
			session.setAttribute("userId", user.getId());
			return "redirect:displayHomePage";
		}
	}

	@GetMapping("editProfileEmail")
	public @ResponseBody Map<String, Boolean> checkEmailExistsEditProfile(@RequestParam("email") String email,
			HttpSession session) {
		LOGGER.info("Inside checkEmailExistsEditProfile()" + email);
		int userId = (int) session.getAttribute("userId");
		String loggedInUserEmail = userRepo.findById(userId).get().getEmail();
		LOGGER.info("Loggedin user same?" + loggedInUserEmail + " " + (!(loggedInUserEmail.equals(email))));
		boolean exists = userRepo.existsByEmail(email) && (!(loggedInUserEmail.equals(email)));
		LOGGER.info("Does email id already exists(True or False)?" + exists);
		Map<String, Boolean> response = new HashMap<>();
		response.put("exists", exists);
		return response;
	}

	@PostMapping("saveProfileInfo")
	public String saveProfileInfo(@ModelAttribute("user") User user, ModelMap modelmap, HttpSession session) {
		LOGGER.info("Inside saveProfileInfo()");
		int userId = (int) session.getAttribute("userId");
		user.setId(userId);
		User savedUser = userRepo.save(user);
		if (savedUser == null) {
			LOGGER.info("Error saving Profile information");
			modelmap.addAttribute("msg", "There was an error saving changes to your profile.Please try again.");
		} else {
			LOGGER.info("Profile information was saved successfully");
			modelmap.addAttribute("msg", "Your changes has been successfully saved.");
		}
		modelmap.addAttribute("user", savedUser);
		return "displayProfileInfo";
	}

	@GetMapping("displayProfileInfo")
	public String displayProfileDeatils(HttpSession session, ModelMap modelmap) {
		LOGGER.info("Inside displayProfileDeatils()");
		int userId = (int) session.getAttribute("userId");
		User user = userRepo.findById(userId).get();
		LOGGER.info("Display profile information of user:" + user);
		modelmap.addAttribute("user", user);
		return "displayProfileInfo";
	}

	@RequestMapping("displayProfile")
	public String displayProfile() {
		return "redirect:displayProfileInfo";
	}

}
