package com.nischitha.spring.FitnessTrackerMyFitnessBuddy.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.entities.Workout;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.repos.WorkoutReopository;
import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.services.FitnessTrackerServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
public class StatisticsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsController.class);

	@Autowired
	WorkoutReopository workoutRepo;
	
	@Autowired
	FitnessTrackerServiceImpl fitnessTrackerServiceImpl;

	@RequestMapping("statistics")
	public String generateGraphs(ModelMap modelmap) {
		LOGGER.info("Inside statistics controller");
		return "displayGenerateGraphs";
	}
	
	@GetMapping("data")
	public  @ResponseBody  Map<String,String> getData(ModelMap modelmap,@RequestParam("category")String category,@RequestParam("subCategory")String subCategory,@RequestParam("metric")String metric,@RequestParam("timeframe")Integer timeframe,HttpSession session){
		LOGGER.info("Inside getData()");
		LOGGER.info("Category is: "+category +" SubCategory is: "+subCategory+" Timeframe is: "+timeframe+" and Metric is: "+metric);
		int userId=(int)session.getAttribute("userId");
		return fitnessTrackerServiceImpl.generateGraphData(category,subCategory,metric, timeframe, userId);

	}

}
