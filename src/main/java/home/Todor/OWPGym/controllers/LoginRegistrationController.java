package home.Todor.OWPGym.controllers;

import java.time.LocalDateTime;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import home.Todor.OWPGym.Repository.UserRepository;
import home.Todor.OWPGym.models.Role;
import home.Todor.OWPGym.models.User;
import home.Todor.OWPGym.service.UserService;

@Controller
@RequestMapping("/")
public class LoginRegistrationController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("")
	public String home(HttpSession session) {
		User loggedUser = (User)session.getAttribute("user");
		
		if(loggedUser != null) {
			if(loggedUser.getRole() == Role.ADMINISTRATOR) {
				return "redirect:/admin";
			}else {
				return "redirect:/member";
			}
		}
		return "redirect:/login";
	}
	
	@GetMapping("login")
	public String Login(HttpSession session) {
		User loggedUser = (User)session.getAttribute("user");
		
		if(loggedUser != null) {
			if(loggedUser.getRole() == Role.ADMINISTRATOR) {
				return "redirect:/admin";
			}else {
				return "redirect:/member";
			}
		}
		return "Login.html";
	}
	
	
	@PostMapping("login")
	public String Login(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpSession session) {
		
		User user = userService.login(username, password);
		if (user != null) {
			if (user.getRole().equals(Role.ADMINISTRATOR)) {
				session.setAttribute("user", user);
				return "redirect:/admin";
			}
			else {
				session.setAttribute("user", user);
				return "redirect:/member";
			}
		}
		return "LoginError.html";
	}
	
	@GetMapping("registration")
	public String registrationForm(HttpSession session) {
		User loggedUser = (User)session.getAttribute("user");
		
		if(loggedUser != null) {
			if(loggedUser.getRole() == Role.ADMINISTRATOR) {
				return "redirect:/admin";
			}else {
				return "redirect:/member";
			}
		}
		return "Registration.html";
	}
	
	@PostMapping("registration")
	public String registration(HttpSession session, @RequestParam("username") String username, 
			@RequestParam("password") String password, @RequestParam("email") String email, 
			@RequestParam("name") String name, @RequestParam("surname") String surname, 
			@RequestParam("dateOfBirth") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime dateOfBirth, 
			@RequestParam("address") String address, @RequestParam("phoneNumber") String phoneNumber) {
		
		User loggedUser = (User)session.getAttribute("user");
		
		if(loggedUser != null) {
			if(loggedUser.getRole() == Role.ADMINISTRATOR) {
				return "redirect:/admin";
			}else {
				return "redirect:/member";
			}
		}
		
		User newUser = new User(username, password, email, name, surname, dateOfBirth, 
								address, phoneNumber, LocalDateTime.now(), Role.MEMBER);
		
		if(userService.register(newUser) == null) {
			return "RegistrationError.html";
		}
		
		userService.register(newUser);
		return "redirect:/";
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		
		session.removeAttribute("user");
		return "redirect:/";
		
	}
}
