package home.Todor.OWPGym.service;

import home.Todor.OWPGym.models.User;

public interface UserService {

	public User login(String username, String password);
	public User register(User newUser);
	public User editUser(User user);
	public User findOne(String username);
}
