package home.Todor.OWPGym.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import home.Todor.OWPGym.Repository.TrainingRepository;
import home.Todor.OWPGym.models.Training;

@Controller
@RequestMapping("/home")
public class TrainingController {

	@Autowired
	TrainingRepository trainingRepository;
	
	
	@GetMapping
	public String home(Model model) {
		ArrayList<Training> trainings = trainingRepository.findAll();
		model.addAttribute("trainings", trainings);
		return "Home.html";
	}
}
