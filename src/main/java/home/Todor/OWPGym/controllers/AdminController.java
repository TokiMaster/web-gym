package home.Todor.OWPGym.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import home.Todor.OWPGym.Repository.UserRepository;
import home.Todor.OWPGym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;
	
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

	@PostMapping("editProfile")
	public String editProfile(HttpSession session, Model model, @RequestParam("newPassword") String newPassword,
		  @RequestParam("repeatNewPassword") String repeatNewPassword, @RequestParam("email") String email,
		  @RequestParam("name") String name, @RequestParam("surname") String surname,
		  @RequestParam("dateOfBirth") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateOfBirth,
		  @RequestParam("address") String address,@RequestParam("phoneNumber") String phoneNumber){

		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		String password = loggedUser.getPassword();

		if(newPassword != "" && repeatNewPassword.equals(newPassword)){
			password = newPassword;
		}else{
			model.addAttribute("error", true);
		}

		if (userService.findOne(loggedUser.getUsername()) != null) {
			User user = new User(loggedUser.getUsername(), password, email, name, surname,
					dateOfBirth, address, phoneNumber, loggedUser.getRegistrationDate(),
					loggedUser.getRole(), loggedUser.isBlocked());
			model.addAttribute("user", user);
			if(userService.editUser(user) == null){
				model.addAttribute("error", true);
				return "EditProfile.html";
			}
		}
		return "redirect:/";
	}

	@GetMapping("allUsers")
	public String allUsers(HttpSession session, Model model){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		ArrayList<User> users = userRepository.findAll();
		model.addAttribute("users", users);

		return "AllUsers.html";
	}

	@GetMapping("allUsers/userInfo")
	public String userInfo(@RequestParam("id") String username, Model model, HttpSession session){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		User user = userRepository.findOne(username);
		if(user != null){
			model.addAttribute("user", user);
			return "User.html";
		}
		return "redirect:/";
	}

	@GetMapping("allUsers/editUser")
	public String editUser(HttpSession session, Model model, @RequestParam("id") String username){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		User user = userRepository.findOne(username);
		if(user != null){
			model.addAttribute("user", user);
			return "EditUser.html";
		}

		return "redirect:/";
	}

	@PostMapping("allUsers/editUser")
	public String editUser(HttpSession session, Model model, @RequestParam("username") String username,
						   @RequestParam("role") Role role, @RequestParam(value = "block", required = false) Boolean isBlocked){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		User editUser = userService.findOne(username);

		boolean block = editUser.isBlocked();

		if(isBlocked != null){
			block = true;
		}

		if (editUser != null) {
			User user = new User(username, editUser.getPassword(), editUser.getEmail(),
					editUser.getName(), editUser.getSurname(), editUser.getDateOfBirth(),
					editUser.getAddress(), editUser.getPhoneNumber(), editUser.getRegistrationDate(),
					role, block);
			model.addAttribute("user", user);
			if(userService.editUser(user) == null){
				model.addAttribute("error", true);
				return "EditUser.html";
			}
		}
		return "redirect:/";
	}

	@GetMapping("addTraining")
	public String addTraining(HttpSession session, Model model) {
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
