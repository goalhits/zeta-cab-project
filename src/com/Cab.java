package com;

import java.util.ArrayList;
import java.util.List;

public class Cab {
	private Long id;
	private String registrationNumber;
	private String carModel;
	private List<User> assignedUsers;
	private List<ErrorMessage> errorMessages;
	
	public Cab(Long id, String model, String registrationNumber) {
		this.id = id;
		this.carModel = model;
		this.registrationNumber = registrationNumber;
		assignedUsers = new ArrayList<User>();
		errorMessages = new ArrayList<ErrorMessage>();
	}
	
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public String getCarModel() {
		return carModel;
	}
	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<ErrorMessage> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<ErrorMessage> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public synchronized List<User> getAssignedUsers() {
		return assignedUsers;
	}
	public void setAssignedUsers(List<User> assignedUsers) {
		this.assignedUsers = assignedUsers;
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
		Cab other = (Cab) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
