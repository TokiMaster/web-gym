package home.Todor.OWPGym.Repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import home.Todor.OWPGym.Repository.UserRepository;
import home.Todor.OWPGym.models.Role;
import home.Todor.OWPGym.models.User;

@Repository
public class UserRepositoryImpl implements UserRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class UserRowCallbackHandler implements RowCallbackHandler{
		
		private ArrayList<User> users = new ArrayList<User>();
		
		@Override
		public void processRow(ResultSet rs) throws SQLException {
			int index = 1;
			
			String username = rs.getString(index++);
			String password = rs.getString(index++);
			String email = rs.getString(index++);
			String name = rs.getString(index++);
			String surname = rs.getString(index++);
			Date dateOfBirth = rs.getTimestamp(index++);
			String address = rs.getString(index++);
			String phoneNumber = rs.getString(index++);
			LocalDateTime registrationDate = rs.getTimestamp(index++).toLocalDateTime();
			Role role = Role.valueOf(rs.getString(index++));
			
			User user = new User(username, password, email, name, surname, dateOfBirth, address, phoneNumber, registrationDate, role);
			users.add(user);
		}
	}
	
	@Override
	public User findOne(String username) {
		String sql = "select * from User where username = ?";
		UserRowCallbackHandler callbackHandler = new UserRowCallbackHandler();
		jdbcTemplate.query(sql, callbackHandler, username);
		if(callbackHandler.users.isEmpty()) {
			return null;
		}
		return callbackHandler.users.get(0);
	}
	
	@Override
	public ArrayList<User> findAll() {
		String sql = "select * from User";
		UserRowCallbackHandler callbackHandler = new UserRowCallbackHandler();
		jdbcTemplate.query(sql, callbackHandler);
		return callbackHandler.users;
	}
	
	@Override
	public void register(User newUser) {
		String sql = "insert into User (username, password, email, name, surname, dateOfBirth, address, phoneNumber, registrationDate, role)"
				+ "values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, newUser.getUsername(), newUser.getPassword(), 
				newUser.getEmail(), newUser.getName(), newUser.getSurname(), newUser.getDateOfBirth(), 
				newUser.getAddress(), newUser.getPhoneNumber(), newUser.getRegistrationDate(), newUser.getRole().name());
	}
}
