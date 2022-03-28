package home.Todor.OWPGym.Repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
			LocalDateTime dateOfBirth = rs.getTimestamp(index++).toLocalDateTime();
			String address = rs.getString(index++);
			String phoneNumber = rs.getString(index++);
			LocalDateTime registrationDate = rs.getTimestamp(index++).toLocalDateTime();
			Role role = Role.valueOf(rs.getString(index++));
			Boolean isBlocked = rs.getBoolean(index++);
			
			User user = new User(username, password, email, name, surname, dateOfBirth,
					address, phoneNumber, registrationDate, role, isBlocked);
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
		String sql = "insert into User (username, password, email, name, surname, " +
				"dateOfBirth, address, phoneNumber, registrationDate, role, isBlocked)"
				+ "values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, newUser.getUsername(), newUser.getPassword(), 
				newUser.getEmail(), newUser.getName(), newUser.getSurname(), Timestamp.valueOf(newUser.getDateOfBirth()),
				newUser.getAddress(), newUser.getPhoneNumber(), newUser.getRegistrationDate(), newUser.getRole().name(),
				newUser.isBlocked());
	}

	@Override
	public void editUser(User user) {
		String sql = "update User set password = ?, email = ?, name = ?,surname = ?, " +
				"dateOfBirth = ?, address = ?, phoneNumber = ? where username = ?";
		jdbcTemplate.update(sql, user.getPassword(), user.getEmail(), user.getName(),
				user.getSurname(), Timestamp.valueOf(user.getDateOfBirth()),
				user.getAddress(), user.getPhoneNumber(), user.getUsername());
	}
}
