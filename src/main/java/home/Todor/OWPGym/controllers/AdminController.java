package home.Todor.OWPGym.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import home.Todor.OWPGym.Repository.HallRepository;
import home.Todor.OWPGym.Repository.TrainingAppointmentRepository;
import home.Todor.OWPGym.Repository.UserRepository;
import home.Todor.OWPGym.models.*;
import home.Todor.OWPGym.service.HallService;
import home.Todor.OWPGym.service.TrainingAppointmentService;
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

	@Autowired
	HallRepository hallRepository;

	@Autowired
	HallService hallService;

	@Autowired
	TrainingAppointmentService trainingAppointmentService;

	@Autowired
	TrainingAppointmentRepository trainingAppointmentRepository;

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

		model.addAttribute("user", userService.findOne(loggedUser.getUsername()));
		return "Profile.html";
	}

	@GetMapping("editProfile")
	public String editProfile(HttpSession session, Model model){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		model.addAttribute("user",  userService.findOne(loggedUser.getUsername()));
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

		boolean block;

		if(isBlocked == null){
			block = false;
		} else {
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
//		model.addAttribute("type", loggedUser);
		return "AddTraining.html";
	}

	@PostMapping("addTraining")
	public String addTraining(HttpSession session, @RequestParam("name") String name,
				  @RequestParam("instructor") String instructor, @RequestParam("description") String description,
				  @RequestParam(value = "photo", required = false) String photo, @RequestParam("typeOfTraining") String typeOfTraining,
				  @RequestParam("price") int price, @RequestParam("type") String trainingType,
				  @RequestParam("intensity") String trainingLVL, @RequestParam("duration") int duration, Model model) {

		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		if (trainingService.addTraining(new Training(name, instructor, description, photo,
				trainingRepository.findOneByTypeOfTraining(typeOfTraining), price,
				TrainingType.valueOf(trainingType), TrainingLVL.valueOf(trainingLVL),
				duration, 4)) == null) {
			model.addAttribute("error", true);
			return "AddTraining.html";
		}
		return "redirect:/";
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

	@GetMapping("allHalls")
	public String allHalls(HttpSession session, Model model){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		ArrayList<Hall> halls = hallRepository.findAll();
		model.addAttribute("halls", halls);
		return "Halls.html";
	}

	@GetMapping("allHalls/editHall")
	public String editHall(HttpSession session, Model model, @RequestParam("id") int id){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		Hall hall = hallRepository.findOne(id);
		if(hall != null){
			model.addAttribute("hall", hall);
			return "EditHall.html";
		}
		return "redirect:/";
	}

	@PostMapping("allHalls/editHall")
	public String editHall(HttpSession session, Model model, @RequestParam("id") int id,
						   @RequestParam("capacity") int capacity){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		Hall hall = hallRepository.findOne(id);

		if(hall != null){
			Hall editHall = new Hall(id, hall.getHallName(), capacity);
			model.addAttribute("hall", editHall);
			if(hallService.editHall(editHall) == null){
				model.addAttribute("error", true);
				return "EditHall.html";
			}
		}

		return "redirect:/";
	}

	@GetMapping("addHall")
	public String addHall(HttpSession session){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		return "AddHall.html";
	}

	@PostMapping("addHall")
	public String addHall(HttpSession session, Model model, @RequestParam("hallName") String hallName,
						  @RequestParam("capacity") int capacity){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		if(hallService.addHall(new Hall(hallName,capacity)) != null){
			return "redirect:/";
		}

		model.addAttribute("error", true);
		return "AddHall.html";
	}

	@GetMapping("addTrainingAppointment")
	public String addTrainingAppointment(HttpSession session, Model model){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		ArrayList<Hall> halls = hallRepository.findAll();
		ArrayList<Training> trainings = trainingRepository.findAll();
		model.addAttribute("halls", halls);
		model.addAttribute("trainings", trainings);

		return "AddTrainingAppointment.html";
	}

	@PostMapping("addTrainingAppointment")
	public String addTrainingAppointment(HttpSession session, Model model,
				 @RequestParam("hall") int hall, @RequestParam("training") int training,
				 @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		if(trainingAppointmentService.addTrainingAppointment(new TrainingAppointment(hallRepository.findOne(hall),
				trainingRepository.findOne(training), startDate)) == null){
			ArrayList<Hall> halls = hallRepository.findAll();
			ArrayList<Training> trainings = trainingRepository.findAll();
			model.addAttribute("halls", halls);
			model.addAttribute("trainings", trainings);
			model.addAttribute("error", true);
			return "AddTrainingAppointment.html";
		}

		return "redirect:/";
	}
}
