package home.Todor.OWPGym.controllers;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import home.Todor.OWPGym.models.Role;
import home.Todor.OWPGym.models.User;

@Controller
@RequestMapping("/member")
public class MemberController {

	@GetMapping
	public String member(HttpSession session) {
		User loggedUser = (User)session.getAttribute("user");
		
		if(loggedUser == null || loggedUser.getRole() != Role.MEMBER ) {
			return "redirect:/";
		}
		
		return "Member.html";
	}
	
}
