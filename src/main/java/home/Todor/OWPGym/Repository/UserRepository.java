package home.Todor.OWPGym.Repository;


import java.util.ArrayList;

import home.Todor.OWPGym.models.User;

public interface UserRepository {

	public User findOne(String username);
	public ArrayList<User> findAll();
	public void register(User newUser);
	public void editUser(User user);
}
