package com;

public class UserRequest implements Runnable {
	private User user;
	
	public UserRequest(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void run() {
		System.out.println("Process of assigning cab to user is initiated..");
	}
}
