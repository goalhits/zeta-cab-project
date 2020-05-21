package com.workers.cab;

import com.Cab;
import com.ErrorMessage;
import com.Gender;
import com.User;
import com.UserRequest;

public class Worker {
	private static int nextWorkerID = 0;

	private BlockingQueue<Worker> idleWorkers;
	private int workerID;
	private BlockingQueue<Runnable> handoffBox;
	private volatile Cab cab;

	private Thread[] internalThreads = new Thread[4];
	private volatile boolean noStopRequested;

	public Worker(BlockingQueue<Worker> idleWorkers) {
		this.idleWorkers = idleWorkers;
		workerID = getNextWorkerID();
		handoffBox = new BlockingQueue<Runnable>(1);

		// just before returning, the thread should be created.
		noStopRequested = true;
		try {
			this.idleWorkers.add(this);
			for (int i = 0; i < 4; i++) {
				Runnable r = () -> runWork();
				internalThreads[i] = new Thread(r);
				internalThreads[i].start();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static synchronized int getNextWorkerID() {
		// notice: sync’d at the class level to ensure uniqueness
		int id = nextWorkerID;
		nextWorkerID++;
		return id;
	}

	public void process(Runnable target) throws InterruptedException {
		handoffBox.add(target);
	}

	private void runWork() {
		try {
			// System.out.println("workerID=" + workerID + ", ready for work");
			// Worker is ready work. This will never block
			// because the idleWorker FIFO queue has
			// enough capacity for all the workers.
			// wait here until the server adds a request
			Runnable r = handoffBox.remove();
			synchronized (cab) {
				assignCabToUser(r);
				if (cab.getErrorMessages().size() != 0) {
					new Thread(() -> runWork()).start();
				}
			}
		} catch (InterruptedException x) {
			Thread.currentThread().interrupt(); // re-assert
		}
	}

	private void assignCabToUser(Runnable r) {
		try {
			UserRequest userRequest = (UserRequest) r;
			User incomingUser = userRequest.getUser();
			String errorMessage;
			if (!User.isRegisteredUser(incomingUser)) {
				errorMessage = "Error for User: " + incomingUser.getEmail() + "User is not registered";
				cab.getErrorMessages().add(new ErrorMessage(400, errorMessage));
				incomingUser.setErrorMessage(new ErrorMessage(400, errorMessage));
				return;
			}
			Integer countMaleCustomers = countCustomers(Gender.MALE);
			Integer countFemaleCustomers = countCustomers(Gender.FEMALE);

			if (cab.getAssignedUsers().size() == 3) {
				if ((countMaleCustomers == 3 && incomingUser.getGender() == Gender.FEMALE)) {
					errorMessage = "Error for User: " + incomingUser.getEmail()
							+ "There should be 2 Males and 2 Females or 4 Males or 4 Females";
					cab.getErrorMessages().add(new ErrorMessage(400, errorMessage));
					incomingUser.setErrorMessage(new ErrorMessage(400, errorMessage));
					return;
				}
				if ((countFemaleCustomers == 3 && incomingUser.getGender() == Gender.MALE)) {
					errorMessage = "Error for User: " + incomingUser.getEmail()
							+ "There should be 2 Males and 2 Females or 4 Males or 4 Females";
					cab.getErrorMessages().add(new ErrorMessage(400, errorMessage));
					incomingUser.setErrorMessage(new ErrorMessage(400, errorMessage));
					return;
				}
			}
			System.out.println("workerID=" + workerID + ", Assigning cab " + cab.getCarModel() + " to user: "
					+ incomingUser.getEmail());

			incomingUser.setAssignedCab(cab);
			cab.getAssignedUsers().add(incomingUser);
			//Remove cab after cab has already allocated 4 people
			if (cab.getAssignedUsers().size() == 4) {
				idleWorkers.remove();
			}
		} catch (Exception runex) {
			// catch any and all exceptions
			System.err.println("Uncaught exception fell through from run()");
			runex.printStackTrace();
		} finally {
			// Clear the interrupted flag (in case it comes back
			// set) so that if the loop goes again, the
			// handoffBox.remove() does not mistakenly
			// throw an InterruptedException.
			Thread.interrupted();
		}
	}

	private synchronized Integer countCustomers(Gender gender) {
		int count = 0;
		for (User user : cab.getAssignedUsers()) {
			if (user.getGender() == gender) {
				count++;
			}
		}
		return count;
	}

	public void stopRequest() {
		System.out.println("workerID=" + workerID + ", stopRequest() received.");
		noStopRequested = false;
		for (int i = 0; i < internalThreads.length; i++) {
			internalThreads[i].interrupt();
		}

	}

	public boolean isAlive() {
		boolean isAlive = true;
		for (int i = 0; i < internalThreads.length; i++) {
			if (internalThreads[i].isAlive()) {
				isAlive &= internalThreads[i].isAlive();
			}
		}
		return isAlive;
	}

	public Cab getCab() {
		return cab;
	}

	public void setCab(Cab cab) {
		this.cab = cab;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + workerID;
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
		Worker other = (Worker) obj;
		if (workerID != other.workerID)
			return false;
		return true;
	}
}