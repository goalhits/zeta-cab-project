package com.workers.cab;

import java.util.LinkedHashSet;
import java.util.Set;

import com.Cab;

public class CabAllocation {
	private BlockingQueue<Worker> idleWorkers;
	private Set<Worker> workerSet;

	public CabAllocation(int numberOfThreads) {
		// make sure that it’s at least one
		numberOfThreads = Math.max(1, numberOfThreads);
		idleWorkers = new BlockingQueue<Worker>(numberOfThreads);
		workerSet = new LinkedHashSet<Worker>();
	}
	
	public void addCab(Cab cab) throws InterruptedException {
		Worker worker = new Worker(idleWorkers);
		worker.setCab(cab);
		workerSet.add(worker);
	}

	public void allocateCabToUser(Runnable target) throws InterruptedException {
		// block (forever) until a worker is available
		Worker worker = idleWorkers.peek();
		worker.process(target);
		if(worker.getCab().getAssignedUsers().size() == 4) {
			workerSet.remove(worker);		
		}
	}
}