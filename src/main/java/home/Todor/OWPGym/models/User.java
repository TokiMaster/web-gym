package home.Todor.OWPGym.models;

import java.time.LocalDateTime;

public class User {
	private String username;
	private String password;
	private String email;
	private String name;
	private String surname;
	private LocalDateTime dateOfBirth;
	private String address;
	private String phoneNumber;
	private LocalDateTime registrationDate;
	private Role role;

	private boolean isBlocked;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public LocalDateTime getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(LocalDateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}
	
	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean blocked) {
		isBlocked = blocked;
	}

	public User() {
		
	}

	public User(String username, String password, String email, String name, String surname, LocalDateTime dateOfBirth,
			String address, String phoneNumber, LocalDateTime registrationDate, Role role, Boolean isBlocked) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.registrationDate = registrationDate;
		this.role = role;
		this.isBlocked = isBlocked;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
