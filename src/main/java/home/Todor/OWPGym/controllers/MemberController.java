package home.Todor.OWPGym.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import home.Todor.OWPGym.Repository.TrainingRepository;
import home.Todor.OWPGym.models.Role;
import home.Todor.OWPGym.models.Training;
import home.Todor.OWPGym.models.User;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	TrainingRepository trainingRepository;
	
	
	@GetMapping
	public String member(HttpSession session, Model model) {
		User loggedUser = (User)session.getAttribute("user");
		
		if(loggedUser == null || loggedUser.getRole() != Role.MEMBER ) {
			return "redirect:/";
		}
		
		ArrayList<Training> trainings = trainingRepository.findAll();
		model.addAttribute("trainings", trainings);
		return "Member.html";
	}
	
	@GetMapping("trainingInfo")
	public String oneTraining(@RequestParam("id") int id, Model model, HttpSession session) {
		
		User loggedUser = (User)session.getAttribute("user");
		
		if(loggedUser == null || loggedUser.getRole() != Role.MEMBER ) {
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
	
}
