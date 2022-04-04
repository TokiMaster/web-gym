package home.Todor.OWPGym.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import home.Todor.OWPGym.Repository.UserRepository;
import home.Todor.OWPGym.models.User;
import home.Todor.OWPGym.service.UserService;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;

	private boolean isValidEmail(String email){
		String regex = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$";
		return Pattern.matches(regex,email);
	}

	@Override
	public User login(String username, String password) {
		User user = userRepository.findOne(username);
		if (user != null) {
			if(user.getPassword().equals(password) && !user.isBlocked()) {
				return user;
			}
		}
		return null;
	}
	
	@Override
	public User register(User newUser) {
		if (userRepository.findOne(newUser.getUsername()) != null
			|| !isValidEmail(newUser.getEmail()) || newUser.getPassword().equals("")
			|| newUser.getEmail().equals("") || newUser.getName().equals("")
			|| newUser.getSurname().equals("")
			|| newUser.getDateOfBirth().isAfter(LocalDateTime.of(2008,12,31,23,59))
			|| newUser.getPhoneNumber().equals("")) {
				return null;
		}
		userRepository.register(newUser);
		return newUser;
	}

	@Override
	public User editUser(User user) {
		if(!isValidEmail(user.getEmail())
		|| user.getEmail().equals("")
		|| user.getName().equals("")
		|| user.getSurname().equals("")
		|| user.getAddress().equals("")
		|| user.getDateOfBirth().isAfter(LocalDateTime.of(2008,12,31,23,59))
		|| user.getPhoneNumber().equals("")){
			return null;
		}
		userRepository.editUser(user);
		return user;
	}

	@Override
	public User findOne(String username) {
		return userRepository.findOne(username);
	}

}
