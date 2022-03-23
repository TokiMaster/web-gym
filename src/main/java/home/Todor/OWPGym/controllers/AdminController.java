package home.Todor.OWPGym.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import home.Todor.OWPGym.models.TypeOfTraining;
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

	@GetMapping("profileInfo")
	public String profileInfo(HttpSession session, Model model){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		model.addAttribute("user", loggedUser);
		return "Profile.html";
	}

	@GetMapping("editProfile")
	public String editProfile(HttpSession session, Model model){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		model.addAttribute("user", loggedUser);
		return "EditProfile.html";
	}
	
	@GetMapping("addTraining")
	public String addTreining(HttpSession session, Model model) {
		User loggedUser = (User)session.getAttribute("user");
		
		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}
		
		ArrayList<TypeOfTraining> typeOfTrainings = trainingRepository.findAllTypes();
		model.addAttribute("typeOfTrainings", typeOfTrainings);
		model.addAttribute("type", loggedUser);
		return "AddTraining.html";
	}
	
	@GetMapping("trainingInfo")
	public String oneTraining(@RequestParam("id") int id, Model model, HttpSession session) {
		
		User loggedUser = (User)session.getAttribute("user");
		
		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}
		
		Training training = trainingRepository.findOne(id);
		if(training != null) {
			model.addAttribute("training", training);
			model.addAttribute("user", loggedUser);
			return "Training.html";
		}
		return "redirect:/";
	}
	
	
	@PostMapping("addTraining")
	public String addTreining(HttpSession session, @RequestParam("name") String name, 
			@RequestParam("instructor") String instructor, @RequestParam("description") String description,
			@RequestParam("photo") String photo, @RequestParam("typeOfTraining") String typeOfTraining,
			@RequestParam("price") int price,@RequestParam("type") String trainingType, 
			@RequestParam("intensity") String trainingLVL, @RequestParam("duration") int duration, Model model) {
		
		User loggedUser = (User)session.getAttribute("user");
		
		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}
		
		Training newTraining = new Training(name, instructor, description, photo,
				trainingRepository.findOneByTypeOfTraining(typeOfTraining), 
				price, TrainingType.valueOf(trainingType), 
				TrainingLVL.valueOf(trainingLVL), duration, 4);
		
		if (trainingService.addTraining(newTraining) == null) {
			model.addAttribute("error", true);
			return "AddTraining.html";
		}
		
		return "redirect:/";
	}
	
	@GetMapping("editTraining")
	public String editTraining(@RequestParam("id") int id, Model model, HttpSession session) {
		User loggedUser = (User)session.getAttribute("user");
		
		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}
		
		Training training = trainingRepository.findOne(id);
		if(training != null) {
			model.addAttribute("training", training);
			return "EditTraining.html";
		}
		return "redirect:/";
	}
	
	@PostMapping("editTraining")
	public String editTraining(@RequestParam("id") int id, HttpSession session, @RequestParam("name") String name, 
			@RequestParam("instructor") String instructor, @RequestParam("description") String description, 
			@RequestParam("photo") String photo, @RequestParam("typeOfTraining") String typeOfTraining, 
			@RequestParam("price") int price, @RequestParam("type") String trainingType, 
			@RequestParam("intensity") String trainingLVL, @RequestParam("duration") int duration, Model model) {
		
		User loggedUser = (User)session.getAttribute("user");
		
		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}
		
		if(trainingRepository.findOne(id) != null) {
			Training editTraining = new Training(id, name, instructor, description, photo,
					trainingRepository.findOneByTypeOfTraining(typeOfTraining), 
					price, TrainingType.valueOf(trainingType), 
					TrainingLVL.valueOf(trainingLVL), duration, 4);
			model.addAttribute("training", editTraining);
			if (trainingService.editTraining(editTraining) == null) {
				model.addAttribute("error", true);
				return "EditTraining.html";
			}
		}

		return "redirect:/";
	}
}
