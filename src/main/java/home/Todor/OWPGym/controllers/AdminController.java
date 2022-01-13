package home.Todor.OWPGym.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import home.Todor.OWPGym.models.Role;
import home.Todor.OWPGym.models.User;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping
	public String admin(HttpSession session) {
		User loggedUser = (User)session.getAttribute("user");
		
		if(loggedUser == null || loggedUser.getRole() != Role.ADMINISTRATOR ) {
			return "redirect:/";
		}
		return "Admin.html";
	}
}
