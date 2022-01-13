package home.Todor.OWPGym.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
public class LoginController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("")
	public String home() {
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
		return "LoginGreska.html";
	}
	
	@GetMapping("home")
	public String homePage() {
		return "Home.html";
	}
	
	
}
