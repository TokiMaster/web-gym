package home.Todor.OWPGym.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import home.Todor.OWPGym.Repository.*;
import home.Todor.OWPGym.models.*;
import home.Todor.OWPGym.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@Autowired
	CommentService commentService;

	@Autowired
	CommentRepository commentRepository;

	@GetMapping
	public String admin(HttpSession session, Model model) {
		User loggedUser = (User)session.getAttribute("user");
		
		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

//		int rating = 0;
//		double average = 0;
		ArrayList<Training> trainings = trainingRepository.findAll();
//		ArrayList<Comment> comments = commentRepository.findAllAcceptedComments(trainingRepository.findOne(id));
//		for(int i = 0; i < trainings.size(); i++){
//			for(int j = 0; i < comments.size(); j++){
//				if(trainings.get(i).getId() == comments.get(j).getTraining().getId() && comments.get(j).getStatus().equals("ACCEPTED")){
//					rating += comments.get(j).getRating();
//				}
//			}
//		}
		model.addAttribute("trainings", trainings);
		return "Admin";
	}

	@GetMapping("profileInfo")
	public String profileInfo(HttpSession session, Model model){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		model.addAttribute("user", userService.findOne(loggedUser.getUsername()));
		return "Profile";
	}

	@GetMapping("editProfile")
	public String editProfile(HttpSession session, Model model){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		model.addAttribute("user",  userService.findOne(loggedUser.getUsername()));
		return "EditProfile";
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
				return "EditProfile";
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

		return "AllUsers";
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
			return "User";
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
			return "EditUser";
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
				return "EditUser";
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
		return "AddTraining";
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
			return "AddTraining";
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
		}

		ArrayList<TrainingAppointment> appointments = new ArrayList<>();

		for(TrainingAppointment appointment : trainingAppointmentRepository.findAll()){
			if(appointment.getTraining().getId() == training.getId()){
				appointments.add(appointment);
				model.addAttribute("appointments", appointments);
			}
		}

		model.addAttribute("comments", commentRepository.findAllAcceptedComments(training));

		return "Training";
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
			return "EditTraining";
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
				return "EditTraining";
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
		return "Halls";
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
			return "EditHall";
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
				return "EditHall";
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

		return "AddHall";
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
		return "AddHall";
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

		return "AddTrainingAppointment";
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
			return "AddTrainingAppointment";
		}

		return "redirect:/";
	}

	@GetMapping("comments")
	public String comments(HttpSession session, Model model){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		ArrayList<Comment> comments = commentRepository.findAll();
		if(comments == null){
			model.addAttribute("error", true);
			ArrayList<Training> trainings = trainingRepository.findAll();
			model.addAttribute("trainings", trainings);
			return "Admin";
		}

		model.addAttribute("comments", comments);

		return "Comments";
	}

	@PostMapping("accept")
	public String acceptComment(HttpSession session, Model model, @RequestParam("id") int id){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		if(commentService.acceptComment(commentRepository.findOne(id)) != null){
			ArrayList<Comment> comments = commentRepository.findAll();
			model.addAttribute("comments", comments);
			return "Comments";
		}

		return "redirect:/";
	}

	@PostMapping("reject")
	public String rejectComment(HttpSession session, Model model, @RequestParam("id") int id){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}

		if(commentService.rejectComment(commentRepository.findOne(id)) != null){
			ArrayList<Comment> comments = commentRepository.findAll();
			model.addAttribute("comments", comments);
			return "Comments";
		}

		return "redirect:/";
	}
}
