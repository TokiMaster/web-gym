package home.Todor.OWPGym.Repository;


import java.util.ArrayList;

import home.Todor.OWPGym.models.User;

public interface UserRepository {
	User findOne(String username);
	ArrayList<User> findAll();
	User login(String username, String password);
	void register(User newUser);
	void editUser(User user);
}
