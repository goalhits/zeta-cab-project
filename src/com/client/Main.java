package com.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import com.Cab;
import com.ErrorMessage;
import com.Gender;
import com.User;
import com.UserRequest;
import com.workers.cab.CabAllocation;
import com.workers.cab.Worker;

public class Main {
	public static Runnable makeRunnable(final String name, final long firstDelay) {
		return new Runnable() {
			public void run() {
				try {
					System.out.println(name + ": starting up");
					Thread.sleep(firstDelay);
					System.out.println(name + ": doing some stuff");
					Thread.sleep(2000);
					System.out.println(name + ": leaving");
				} catch (InterruptedException ix) {
					System.out.println(name + ": got interrupted!");
					return;
				} catch (Exception x) {
					x.printStackTrace();
				}
			}

			public String toString() {
				return name;
			}
		};
	}

	public static void main(String[] args) throws Exception {
		try {
			CompletableFuture<CabAllocation> future = CompletableFuture.supplyAsync(() -> {
				CabAllocation pool = new CabAllocation(3);
				try {
					pool.addCab(addCabRequest(1L, "SwiftDezire", "KA51MM3232"));
					pool.addCab(addCabRequest(2L, "i10", "KA05MM3833"));
					pool.addCab(addCabRequest(3L, "i20", "KA21MM1122"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return pool;
			});

			CabAllocation pool = future.get();

			UserRequest userRequest = makeUserRequest(1L, "A", "A_S", "a@gmail.com", Gender.MALE, "+912028282911");
			User.registerUser(userRequest.getUser());
			pool.allocateCabToUser(userRequest);
			printErrors(userRequest.getUser());

			userRequest = makeUserRequest(2L, "B", "B_S", "b@gmail.com", Gender.MALE, "+912028282912");
			User.registerUser(userRequest.getUser());
			pool.allocateCabToUser(userRequest);
			printErrors(userRequest.getUser());

			userRequest = makeUserRequest(3L, "C", "C_S", "c@gmail.com", Gender.MALE, "+912028282913");
			User.registerUser(userRequest.getUser());
			pool.allocateCabToUser(userRequest);
			printErrors(userRequest.getUser());

			userRequest = makeUserRequest(4L, "D", "D_S", "d@gmail.com", Gender.FEMALE, "+912028282914");
			User.registerUser(userRequest.getUser());
			pool.allocateCabToUser(userRequest);
			printErrors(userRequest.getUser());

			userRequest = makeUserRequest(5L, "E", "E_S", "e@gmail.com", Gender.MALE, "+912028282915");
			User.registerUser(userRequest.getUser());
			pool.allocateCabToUser(userRequest);
			printErrors(userRequest.getUser());
			
			userRequest = makeUserRequest(6L, "F", "F_S", "f@gmail.com", Gender.MALE, "+912028282916");
			User.registerUser(userRequest.getUser());
			pool.allocateCabToUser(userRequest);
			printErrors(userRequest.getUser());

		} catch (InterruptedException ix) {
			ix.printStackTrace();
		}
	}

	private static void printErrors(User user) {
		if (user.getErrorMessage() != null) {
			System.out.println(
					"Printing error message=" + "for User = " + user.getFirstName() + " " + user.getLastName());
			System.out.println(user.getErrorMessage().getCode());
			System.out.println(user.getErrorMessage().getMessage());
		}
	}

	private static UserRequest makeUserRequest(Long id, String firstName, String lastName, String email, Gender gender,
			String phoneNumber) {
		User user = new User(id, firstName, lastName, email, gender, phoneNumber);
		UserRequest userRequest = new UserRequest(user);
		return userRequest;
	}

	private static Cab addCabRequest(Long id, String model, String registrationNumber) {
		return new Cab(id, model, registrationNumber);
	}
}
