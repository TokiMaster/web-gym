package home.Todor.OWPGym.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import home.Todor.OWPGym.Repository.UserRepository;
import home.Todor.OWPGym.models.User;
import home.Todor.OWPGym.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public User login(String username, String password) {
		User user = userRepository.findOne(username);
		if (user != null) {
			if(user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}
	
	@Override
	public User register(User newUser) {
		if (userRepository.findOne(newUser.getUsername()) != null) {	
				return null;
		}
		userRepository.register(newUser);
		return newUser;
	}

}
