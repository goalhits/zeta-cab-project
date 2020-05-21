package com;

import java.util.LinkedHashMap;
import java.util.Map;

public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private Gender gender;
	private String password;
	private String phoneNumber;
	private Cab assignedCab;
	private ErrorMessage errorMessage;
	
	public User (Long id, String firstName, String lastName, String email, Gender gender, String phoneNumber) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
	}
	
	private static Map<String, User> userRegisteredWithPhon =  new LinkedHashMap<String, User>();
	private static Map<String, User> userRegisteredWithEmail = new LinkedHashMap<String, User>();
	
	public static void registerUser(User user) throws Exception {
		//TODO: perform empty check as well and validate for name etc.
		if(user.getPhoneNumber() == null && user.getEmail() == null) {
			throw new Exception("User should provide either phone or email");				
		}
		
		if(user.getPhoneNumber() != null) {
			userRegisteredWithPhon.put(user.phoneNumber, user);
		}
		
		if(user.getEmail() != null) {
			userRegisteredWithEmail.put(user.email, user);
		}
	}
	
	public static boolean isRegisteredUser(User user) throws Exception {
		if(user.getPhoneNumber() != null && !userRegisteredWithPhon.containsKey(user.getPhoneNumber())) {
			return false;
		}
		if(user.getEmail() != null && !userRegisteredWithEmail.containsKey(user.getEmail())) {
			return false;
		}
		return true;
	}
	
	public void removeRegisteredUser(User user) {
		if(user.getPhoneNumber() != null) {
			userRegisteredWithPhon.remove(user.phoneNumber);
		}
		
		if(user.getEmail() != null) {
			userRegisteredWithEmail.remove(user.email);
		}
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Cab getAssignedCab() {
		return assignedCab;
	}

	public void setAssignedCab(Cab assignedCab) {
		this.assignedCab = assignedCab;
	}
	
	
}
