package home.Todor.OWPGym.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import home.Todor.OWPGym.Repository.TrainingRepository;
import home.Todor.OWPGym.models.Role;
import home.Todor.OWPGym.models.Training;
import home.Todor.OWPGym.models.TrainingLVL;
import home.Todor.OWPGym.models.TrainingType;
import home.Todor.OWPGym.models.User;
import home.Todor.OWPGym.service.TrainingService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	TrainingRepository trainingRepository;
	
	@Autowired
	TrainingService trainingService;
	
	@GetMapping
	public String admin(HttpSession session, Model model) {
		User loggedUser = (User)session.getAttribute("user");
		
		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}
		ArrayList<Training> trainings = trainingRepository.findAll();
		model.addAttribute("trainings", trainings);
		return "Admin.html";
	}
	
	@GetMapping("addTraining")
	public String addTreining(HttpSession session) {
		User loggedUser = (User)session.getAttribute("user");
		
		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}
//		model.addAttribute("error", true);
		return "AddTraining.html";
	}
	
	@PostMapping("addTraining")
	public String addTreining(HttpSession session, @RequestParam("name") String name, 
			@RequestParam("instructor") String instructor, @RequestParam("description") String description, 
			@RequestParam("typeOfTraining") String typeOfTraining, @RequestParam("price") int price,
			@RequestParam("startDate") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime startDate,
			@RequestParam("duration") int duration, Model model) {
		
		User loggedUser = (User)session.getAttribute("user");
		
		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}
		
		Training newTraining = new Training(name, instructor, description,
				trainingRepository.findOneByTypeOfTraining(typeOfTraining), 
				price, TrainingType.INDIVIDUAL, TrainingLVL.INTERMEDIATE,
				startDate, duration, 4);
		
		if (trainingService.addTraining(newTraining) == null) {
			model.addAttribute("error", true);
			return "AddTraining.html";
		}
		
		trainingService.addTraining(newTraining);
		
		return "redirect:/";
	}
}
