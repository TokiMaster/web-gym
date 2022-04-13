package home.Todor.OWPGym.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import home.Todor.OWPGym.Repository.TrainingAppointmentRepository;
import home.Todor.OWPGym.Repository.WishListRepository;
import home.Todor.OWPGym.models.*;
import home.Todor.OWPGym.service.UserService;
import home.Todor.OWPGym.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import home.Todor.OWPGym.Repository.TrainingRepository;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	TrainingRepository trainingRepository;

	@Autowired
	UserService userService;

	@Autowired
	TrainingAppointmentRepository trainingAppointmentRepository;

	@Autowired
	WishListService wishListService;

	@Autowired
	WishListRepository wishListRepository;
	
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

	@GetMapping("profileInfo")
	public String profileInfo(HttpSession session, Model model){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.MEMBER ) {
			return "redirect:/";
		}

		model.addAttribute("user", userService.findOne(loggedUser.getUsername()));
		return "Profile.html";
	}

	@GetMapping("editProfile")
	public String editProfile(HttpSession session, Model model){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.MEMBER ) {
			return "redirect:/";
		}

		model.addAttribute("user", userService.findOne(loggedUser.getUsername()));
		return "EditProfile.html";
	}

	@PostMapping("editProfile")
	public String editProfile(HttpSession session, Model model, @RequestParam("newPassword") String newPassword,
		  @RequestParam("repeatNewPassword") String repeatNewPassword, @RequestParam("email") String email,
		  @RequestParam("name") String name, @RequestParam("surname") String surname,
		  @RequestParam("dateOfBirth") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateOfBirth,
		  @RequestParam("address") String address,@RequestParam("phoneNumber") String phoneNumber){

		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.MEMBER ) {
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
		}

		ArrayList<TrainingAppointment> appointments = new ArrayList<>();

		for(TrainingAppointment appointment : trainingAppointmentRepository.findAll()){
			if(appointment.getTraining().getId() == training.getId()){
				appointments.add(appointment);
			}
		}
		model.addAttribute("appointments", appointments);
		return "Training.html";
	}

	private ArrayList<TrainingAppointment> appointments = new ArrayList<>();

	@PostMapping("addToCart")
	public String addToCart(HttpSession session, Model model, @RequestParam(value = "id", required = false) int id){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.MEMBER ) {
			return "redirect:/";
		}

		TrainingAppointment appointment = trainingAppointmentRepository.findOne(id);

		if(appointment != null && !appointments.contains(appointment)){
			appointments.add(appointment);
			session.setAttribute("appointments", appointments);
			return "redirect:/";
		}

		model.addAttribute("error", true);

		return "redirect:/";
	}

	@PostMapping("addToWishList")
	public String addToWishList(HttpSession session, Model model, @RequestParam("trainingId") int id){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.MEMBER ) {
			return "redirect:/";
		}

		Training training = trainingRepository.findOne(id);

		if (training != null) {
			wishListService.addToWishList(new WishList(loggedUser, training));
		}

		model.addAttribute("errors", true);
		return "redirect:/";
	}

	@GetMapping("cart")
	public String cart(HttpSession session, Model model){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.MEMBER ) {
			return "redirect:/";
		}

		ArrayList<TrainingAppointment> appointments = (ArrayList<TrainingAppointment>) session.getAttribute("appointments");
		if(appointments == null){
			model.addAttribute("error", true);
			ArrayList<Training> trainings = trainingRepository.findAll();
			model.addAttribute("trainings", trainings);
			return "Member.html";
		}
		model.addAttribute("appointments", appointments);

		double total = 0;
		for (TrainingAppointment appointment : appointments) {
			total += appointment.getTraining().getPrice();
		}
		model.addAttribute("total", total);

		return "Cart.html";
	}

	@PostMapping("removeFromCart")
	public String removeFromCart(HttpSession session, @RequestParam("remove") int id){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.MEMBER ) {
			return "redirect:/";
		}

		ArrayList<TrainingAppointment> appointments = (ArrayList<TrainingAppointment>) session.getAttribute("appointments");
		TrainingAppointment appointment = trainingAppointmentRepository.findOne(id);
		if(appointment != null && appointments.contains(appointment)){
			appointments.remove(appointment);
			return "redirect:/member/cart";
		}

		return "redirect:/";
	}

	@GetMapping("wishList")
	public String wishList(HttpSession session, Model model){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.MEMBER ) {
			return "redirect:/";
		}

		ArrayList<WishList> wishLists = wishListRepository.findUsersWishList(loggedUser.getUsername());
		if(wishLists == null){
			model.addAttribute("errors",true);
			ArrayList<Training> trainings = trainingRepository.findAll();
			model.addAttribute("trainings", trainings);
			return "Member.html";
		}

		model.addAttribute("wishList", wishLists);

		return "WishList.html";
	}

	@PostMapping("removeFromWishList")
	public String removeFromWishList(HttpSession session, @RequestParam("remove") int id){
		User loggedUser = (User)session.getAttribute("user");

		if(loggedUser == null || loggedUser.getRole() != Role.MEMBER ) {
			return "redirect:/";
		}

		WishList training = wishListRepository.findOne(id);
		if(training != null){
			wishListRepository.deleteFromWishList(training);
			return "redirect:/member/wishList";
		}

		return "redirect:/";
	}
}
