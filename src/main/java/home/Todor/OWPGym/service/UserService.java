package home.Todor.OWPGym.service;

import home.Todor.OWPGym.models.User;

public interface UserService {
	User login(String username, String password);
	User register(User newUser);
	User editUser(User user);
	User findOne(String username);
}
